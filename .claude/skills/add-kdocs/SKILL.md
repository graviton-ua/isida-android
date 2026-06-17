---
name: add-kdocs
description: Add or update KDoc documentation on Kotlin declarations following Kotlin style — summary line, @param/@return/@throws/@property, inline [links], and rules for suspend/extension/sealed/enum/typealias/deprecated elements. Skips private members and trivial boilerplate. Use when asked to add, write, document, or update KDocs/documentation in `.kt` files.
---

# Add KDocs

Add high-quality, idiomatic KDoc to Kotlin source — accurate, useful in IDE hover, and conformant to the Kotlin documentation style guide.

## How to apply

- **A file** → read it, add/update KDoc, apply the changes with Edit.
- **A directory** → recursively scan `*.kt`, skipping `build/`, generated sources (`generated/`, `*.generated.kt`), and intentionally-minimal test fixtures.
- **Inline code** → return the documented source.

Edit files in place (insert new blocks above the element; replace outdated ones). After changes, summarize per file which elements got `NEW` vs `UPDATE` docs. If nothing needs it, say "No missing or outdated KDocs found."

## What to document

`public` and `internal`: classes, interfaces, objects, companion objects, functions (incl. top-level and extension), properties (constructor- and body-declared), `typealias`, enum classes (+ non-obvious entries), sealed hierarchies (+ each subclass).

**Do not document:** `private`/`protected` members; overrides that add no behavior over a fully-documented parent (use `{@inheritDoc}` if anything); boilerplate `toString`/`equals`/`hashCode`/`copy`/`componentN`; self-evident getters/setters (`val userId: String`); generated code.

## Status criteria

- **NEW** — no `/** … */` block precedes the element.
- **UPDATE** — a block exists but is stale: `@param` names/count mismatch; `@return` missing on non-`Unit` (or present on `Unit`); `@throws` doesn't match actual throws/contract; `@property` missing for a constructor property; undocumented generic type params; summary misleading after a rename/logic change; `suspend` fn missing special cancellation/dispatcher notes; `@Deprecated` missing a migration path.
- **OK** — present, accurate, complete → skip.

## Quality standards

- **Summary line** — first sentence must stand alone (it shows in IDE hover/API index). Third-person present tense: "Returns the user profile", not "This function returns…". One sentence, ideally < 120 chars. Blank line before any extended description.
- **Omit tags that add nothing** beyond the name/type. A `@property userId The ID of the user` on `userId` is noise — a good summary alone is better.
- **Inline links** use KDoc syntax `[ClassName]` / `[ClassName.member]`, plus `@see` — never Javadoc `{@link}`.
- **Markdown** is supported — use `` `code` ``, fenced `kotlin` examples, and lists purposefully.

## Tags by element type

**Classes / interfaces / objects / sealed:**

```kotlin
/**
 * Summary sentence.
 *
 * Extended description if needed.
 *
 * @param T Generic type parameter (if applicable).
 * @property name For each primary-constructor property.
 * @constructor What the primary constructor does (omit if obvious).
 * @see RelatedClass
 */
```

Body-declared properties get their own KDoc block above them (not `@property` in the class doc). For sealed types, document the hierarchy purpose on the parent and each subclass separately. For companion objects, document non-obvious factories/constants individually.

**Functions:**

```kotlin
/**
 * Summary sentence.
 *
 * @param paramName Description.
 * @return Description (omit if return type is `Unit`).
 * @throws ExceptionType Condition under which it's thrown.
 */
```

**Suspend functions** — don't document the implicit `CancellationException`. Do note: a required dispatcher (e.g. `Dispatchers.IO`), side effects on cancellation (partial writes, cleanup), or non-cancellable-context safety.

**Extension functions** — use `@receiver` to describe the receiver's expected state/contract when non-obvious.

**Deprecated** — always include a `@deprecated` description with a migration path, alongside the `@Deprecated(...)` annotation (use `ReplaceWith` where possible).

**Enums** — document the class; document each entry only when non-obvious:

```kotlin
/** Current state of an order in the fulfilment pipeline. */
enum class OrderStatus {
    /** Placed but not yet confirmed by the seller. */
    PENDING,
    DELIVERED, // obvious — no KDoc
}
```

## Common mistakes

| Mistake | Correct |
|---|---|
| `{@link Foo}` | `[Foo]` |
| `@return` on a `Unit` function | Omit it |
| Documenting `private` members | Skip |
| `@property` for body-declared properties | Own KDoc block above them |
| "This function returns…" | "Returns…" (third-person, no preamble) |
| Documenting `CancellationException` on every suspend fun | Only special cancellation behavior |
| `@param` that restates the name | Omit when it adds nothing |
| Documenting `toString`/`equals` on data classes | Skip — boilerplate |
