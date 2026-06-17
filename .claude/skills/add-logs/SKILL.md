---
name: add-logs
description: Add or review production-grade Kotlin/KMP logging using Kermit, following this project's conventions — logger setup, log levels, exception handling, coroutine/Flow logging, message format, and the rule never to log sensitive data. Use when asked to add, instrument, review, or adjust logging in `.kt` files.
---

# Add logging

Add comprehensive, production-grade logging to Kotlin code — observable, debuggable, and maintainable, without log noise, performance regressions, or security risks.

## How to apply

- **A file** → read it, add logging, apply the changes with Edit.
- **A directory** → scan `*.kt` files recursively and update each that needs it.
- **Inline code** → return the modified source.

Edit files in place (don't just print them). After changes, give a one-line summary per file of what logging was added and why. If a file needs nothing, say "No logging changes required" with a brief reason.

## RULE 0 — Never log sensitive data

This overrides every other rule. **Never** log, even at `DEBUG`:

- PII: full name, email, phone, address, date of birth
- Payment/financial data: card numbers, bank details, amounts tied to a user
- Auth material: passwords, tokens, API keys, session IDs, OAuth codes
- Device identifiers usable for fingerprinting
- Any value whose name/type suggests it is sensitive (`password`, `token`, `secret`, `cardNumber`, `iban`)

When you must log that a value exists, log its presence or a redacted placeholder — never the value:

```kotlin
// BAD
logger.d { "Auth token: $token" }

// GOOD
logger.d { "Auth token present: ${token != null}" }
logger.d { "Processing payment for order: $orderId" }  // ID only, never amount/card
```

## 1. Logger initialization

Use **Kermit** (`co.touchlab.kermit`) — the project's KMP-compatible logger. Create a `private val` logger, lazy, tagged with the class name:

```kotlin
private val logger by lazy { Logger.withTag("AdsRepository") }
```

- **Companion objects** — declare the logger inside the companion when needed in static/factory context.
- **Top-level functions / files** — declare at file top with a module concept name (`"NetworkUtils"`).
- **Nested classes** — qualified tag: `Logger.withTag("OrderFlow.PaymentStep")`.
- **Interfaces** — no logger; add it only to implementing classes.

| Context | Tag | Example |
|---|---|---|
| Class | Exact class name | `"CheckoutViewModel"` |
| Nested class | `"Parent.Nested"` | `"OrderFlow.PaymentStep"` |
| Companion object | Enclosing class name | `"UserRepository"` |
| Top-level file | Module/file concept | `"NetworkUtils"` |
| Anonymous / object expr | Nearest enclosing named class | `"ImagePickerLauncher"` |

## 2. Log levels

| Level | Method | When |
|---|---|---|
| Verbose | `logger.v { }` | Fine-grained traces for deep debugging only |
| Debug | `logger.d { }` | Developer detail: intermediate values, branch decisions, state snapshots |
| Info | `logger.i { }` | Significant lifecycle events: operation started/completed, user action, background task scheduled |
| Warning | `logger.w { }` | Caught and handled gracefully: validation failures, domain-rule violations, fallback used, retry, missing optional data. Include the exception. |
| Error | `logger.e { }` | Unexpected system failures: network/DB errors, malformed responses, unrecoverable states. An engineer should investigate. Include the exception. |

**Always use lambda syntax** — defers string construction until the level is active:

```kotlin
logger.d { "Processing item: ${item.id}, state: ${item.state}" }   // CORRECT
logger.d("Processing item: ${item.id}")                            // WRONG — always builds the string
```

## 3. What to log (and not)

**Info** for significant operations: network requests/responses (URL + status, never body with user data), DB read/write, user actions, background-task lifecycle, auth flows (never credentials), behavior-affecting feature-flag checks, cache hits/misses for expensive ops.

**Debug** for: intermediate state in multi-step ops, non-obvious branch decisions, retry attempts, transformation results (IDs/counts only).

**Never add logging to:** simple getters/computed properties; `toString`/`equals`/`hashCode`/`copy`; trivial mappers (`fun toDto()`); high-frequency callbacks (`onDraw`, `onMeasure`, `onBindViewHolder`, `onScrollChanged`); per-frame/per-item render functions; tight loops.

**Entry/exit logs** only on significant operations (network, DB, user action, background task) — not on every public function.

**Loops** — log aggregate context before/after, never per-item:

```kotlin
logger.d { "Processing ${items.size} items" }
items.forEach { process(it) }
// to trace individuals while debugging, cap it:
items.forEachIndexed { i, item -> if (i < 5) logger.v { "Item[$i]: id=${item.id}" }; process(item) }
```

## 4. Exception logging

Key question: **should an engineer investigate this?**

- **`logger.w(e) { }`** — expected, recoverable business flow (validation, domain-rule violation). No action needed.
- **`logger.e(e) { }`** — unexpected system/infra/contract failure (network, DB, parse). An engineer should look.

```kotlin
} catch (e: CancellationException) {
    throw e  // never log cancellation — always rethrow
} catch (e: ValidationException) {
    logger.w(e) { "Form validation failed on checkout" }
} catch (e: IOException) {
    logger.e(e) { "Failed to fetch orders from API" }
}
```

Rules for both: pass the exception as the **first argument** (captures the stack trace); the message states the **intent** ("what we were trying to do"), not the exception type; include IDs/context, never sensitive values; never swallow silently; never log `CancellationException` — rethrow it.

## 5. Coroutines & Flow

- **Suspend functions** — log at the call site of significant operations, not inside every suspend fun. Rethrow `CancellationException` before any catch that logs.
- **Flow** — log on `onStart` / `catch` / `onCompletion`, never on every emission:

```kotlin
someFlow
    .onStart { logger.d { "Started collecting order status updates" } }
    .catch { e -> logger.e(e) { "Unexpected error in order status stream" } }
    .onCompletion { logger.d { "Order status stream completed" } }
    .collect { updateUi(it) }
```

## 6. Message format

Greppable and consistent:

- Start: `"Fetching product details for listing: $listingId"`
- Result: `"Fetched product details: ${product.id}, images: ${product.images.size}"`
- Failure: `"Failed to submit bid for listing: $listingId"`
- Warning/fallback: `"Cache miss for listing: $listingId, fetching from network"`

Lowercase body; include relevant IDs but never full objects; be specific (avoid `"Error occurred"`); no trailing punctuation.

## Common mistakes

| Mistake | Correct |
|---|---|
| Logging every function entry/exit | Only significant operations |
| `logger.d("msg")` | `logger.d { "msg" }` always |
| Logging PII/tokens | IDs and presence only |
| Logging inside a loop | Count before/after |
| Logging `CancellationException` | Rethrow without logging |
| Logging every Flow emission | `onStart`/`catch`/`onCompletion` |
| Generic `"Error occurred"` | Specific intent message |
| Logger in an interface | Only in implementing classes |
| `logger.e(e)` for validation/domain | `logger.w(e)` |
| `logger.w(e)` for network/DB/parse | `logger.e(e)` |
| `logger.e { "error" }` without exception | `logger.e(exception) { "context" }` |
