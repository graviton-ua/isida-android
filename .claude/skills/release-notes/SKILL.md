---
name: release-notes
description: Generate release notes for an ISIDA release — GitHub (technical, Android + Desktop) and Google Play (end-user, localized en-US/ru-RU/uk-UA). Asks for the tag range, gathers git log + diff itself, classifies changes per target, and enforces Google Play's locale set and character limit. Use when asked to write or generate release notes for a version/tag/release. Output only — does not publish.
---

# Release notes

Generate clear, accurate release notes for **ISIDA** (Kotlin Multiplatform app, **Android + Desktop/JVM**) in two formats: **GitHub** (technical) and **Google Play** (end-user, localized). **Output only — never publish**; the user creates the GitHub release and Play Console entries themselves.

**Targets & how they ship** (see `.github/workflows/`):
- **Android** is the product. A `vX.Y.Z` tag builds an AAB → Firebase App Distribution → Google Play `internal` track (`ua.isida`). → needs **GitHub + Google Play** notes.
- **Desktop (Windows/macOS)** is primarily a fast test/preview build, published as installer assets on a **GitHub Release**. → covered by the **GitHub** notes only; never gets Play notes.
- **iOS** does not exist yet (possible future target). The App Store section in STEP 7 is a **dormant stub** — produce it only if the user explicitly asks or an `iosApp/` directory exists.

## STEP 0 — Inputs (ask first)

Before gathering anything, confirm with the user:

1. **Tag range** — from which tag to which tag. **Always ask; never assume the previous tag.** Tags/versions are often skipped or never published, so the base is the *last released* tag, which may not be the immediately-preceding one. List recent tags to help them choose:
   ```bash
   git tag --sort=-v:refname | head -20
   ```
2. **Scope** — `Google Play` (Android store notes + GitHub), `GitHub only` (e.g. a Desktop-only or internal build), or `Both` (default). If unsure, default to **Both**.

## STEP 1 — Gather git data yourself

Gather **both** log and diff — commit messages alone do not show the full picture. Scale the depth to the change:

```bash
git log <from>..<to> --pretty="%h %s%n%b"   # messages + bodies (BREAKING CHANGE markers)
git diff --stat <from>..<to>                 # file-level scope → target classification
git diff <from>..<to> -- <path>              # real changes; read deeper where commit msgs are vague or hunks are large
```

Lead with the log (the conventional-commit prefixes are structured signal), use `--stat` to map scope, and read actual diff hunks to confirm user-facing impact — especially when a message is terse or a change is large. Ignore commits whose subject contains `[NOT COMPILABLE]` or other WIP markers unless their changes were later completed within the range.

## STEP 2 — Target classification

Classify each change by file path:

| Path pattern | Classification |
|---|---|
| `androidApp/**`, `**/*.android.kt`, `**/AndroidManifest.xml`, `**/google-services.json`, `**/res/values*/**` | **Android-only** |
| `desktopApp/**`, `**/*.jvm.kt`, `**/jvmMain/**`, `**/desktopMain/**` | **Desktop-only** |
| `iosApp/**`, `**/*.ios.kt`, `**/*.swift` (future) | **iOS-only** |
| `shared/**`, `common/**`, `core/**`, `data/**`, `domain/**`, `ui/**`, `**/commonMain/**`, root `build.gradle.kts`, `settings.gradle.kts`, `gradle/libs.versions.toml`, `gradle.properties`, `gradle/build-logic/**` | **cross-platform** |
| Anything else (CI, docs, top-level config) | **cross-platform** unless a platform-specific path is touched in the same hunk |

**Conflicts:** classify a mixed commit by dominant intent from its message (`fix: desktop window crash…` → Desktop-only even if it touches shared code). A commit touching both `androidApp/` and `desktopApp/` → cross-platform.

**Google Play notes include only Android-only + cross-platform changes.** Desktop-only changes appear in the GitHub notes but **never** in Google Play.

## STEP 3 — Accuracy guardrails (all sections)

1. **No fabrication.** Every line traces to a commit or diff hunk. Don't infer features from names or invent benefits.
2. **Conservative on uncertainty.** If user impact is unclear, describe it broadly ("Improvements to device scanning") rather than guessing.
3. **No internal artifacts in store notes.** Strip commit hashes, author names, `[NOT COMPILABLE]`/WIP markers, internal URLs, code identifiers, and module names from Google Play. GitHub may keep hashes and identifiers.
4. **Empty release.** If there are no user-facing changes (deps/CI/refactor only), each Play locale gets a single short "stability and performance improvements" message; GitHub still lists the technical changes.

## STEP 4 — Conventional-commit categorization

ISIDA uses lowercase **Conventional Commits**, sometimes scoped: `type:` or `type(scope): summary`. Map the `type` to a category, but **let the diff override** (a `refactor:` with visible UI impact is an enhancement).

| Prefix | Default category | In Google Play? |
|---|---|---|
| `feat:` | ✨ New Features | ✅ |
| `fix:` | 🐛 Bug Fixes | ✅ |
| `ui:` / `feat(ui):` | 🚀 Enhancements | ✅ |
| `perf:` | 🚀 Enhancements | ✅ |
| `refactor:` | 🛠️ Technical (→ 🚀 if diff shows user-visible change) | only if user-visible |
| `build:` / `chore:` / `docs:` / `test:` / `debug:` | 🛠️ Technical | ❌ |
| `BREAKING CHANGE:` in body | ⚠️ Breaking Changes | ✅ if user-visible |

When rendering a bullet, **strip the `type:`/`type(scope):` prefix** from the text and use it only for categorization.

## STEP 5 — GitHub Release Notes (always)

Audience: developers/stakeholders. Wrap the whole thing in one ` ```markdown ` fenced block.

1. **Title:** `# {{Version}}` (append ` — Desktop build` only if the release is Desktop-only).
2. **`### Summary`** — 3–5 sentences on what changed and why it matters.
3. **Synthesize**, don't list raw commits. Combine intent (log) + impact (diff).
4. **Target tags:** prefix only target-specific bullets with `[Android]` or `[Desktop]`. Cross-platform bullets get **no** tag.
5. **Categories** (omit empty ones), highest user impact first within each:
   - ✨ New Features · 🚀 Enhancements & Improvements · 🐛 Bug Fixes · ⚠️ Breaking Changes · 🛠️ Technical & Internal Changes
6. **Style:** professional, positive; rewrite commits into readable impact (`fix: npe in scan service` → "Fixed a crash when scanning for devices."). Backticks for code identifiers. End each bullet with its short hash in parens: `… (a1b2c3d)`.

## STEP 6 — Google Play Release Notes (Google Play / Both)

Audience: end users, plain tone. Include **Android-only + cross-platform**; exclude Desktop-only and iOS-only.

- **Char budget:** aim 400–480; hard cap **500 chars per locale** between the tags (the `<locale>` tags don't count). Count after writing each block; shorten until it fits. Don't print the count.
- **Structure per locale:** one short opener; 2–4 `•` bullets; optional one-line closing.
- **Order:** highest impact first.
- **Tone:** clear and friendly, second-person, jargon-free; localize **idiomatically**, never word-for-word.
- **Locales (Play Console codes):** `en-US`, `ru-RU`, `uk-UA` — these mirror the app's shipped resources (`values`, `values-ru`, `values-uk`). Confirm against the Play Console listing if it differs.

Output — one fenced block with `<locale>…</locale>` tags:

```
<en-US>
…
</en-US>
<ru-RU>
…
</ru-RU>
<uk-UA>
…
</uk-UA>
```

## STEP 7 — App Store Release Notes (iOS — dormant stub)

iOS is not a current target. Produce this section **only** if the user explicitly asks or an `iosApp/` directory exists. When activated: end-user marketing tone, no markdown, ~150–200 words, hard cap 4000 chars per locale, one positive opener + 2–5 standalone sentences + a short CTA. Confirm the App Store Connect locale set with the user before generating (do not assume the Google Play locales apply).

## STEP 8 — Self-check before output

- [ ] GitHub section present; Google Play follows the scope rules; iOS stub stays dormant unless activated.
- [ ] Every line traces to a commit or diff hunk — nothing invented; `[NOT COMPILABLE]`/WIP commits excluded.
- [ ] Conventional-commit `type:` prefixes stripped from rendered text, used only for categorization.
- [ ] Cross-platform GitHub bullets have **no** target tag; Desktop-only changes absent from Google Play.
- [ ] Google Play (if present): 3 locales (`en-US`, `ru-RU`, `uk-UA`); each ≤ 500 chars between tags.
- [ ] Empty-release fallback applied to Play section if there are no user-facing changes.
