# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## What this is

ISIDA is a **Kotlin Multiplatform (KMP)** app targeting **Android and Desktop (JVM)**. Android is the product (shipped to Google Play); Desktop is mainly a fast build/test surface for the shared UI and logic. The app talks to hardware devices over **Bluetooth**. Business logic, data, and UI are shared across both platforms via KMP modules. Android lives in `androidApp`, Desktop in `desktopApp`; everything else is shared modules consumed by both. iOS is a *possible future* target — not present today.

## Build & test commands

`./gradlew` wrapper. **`:androidApp` and `:desktopApp` are the only application modules; every other module is a variant-less KMP library** (targets: `jvm`, `android`; no iOS). The Android app uses plain `debug`/`release` build types — **no product flavors**. Per-module compile tasks aren't listed by the root `./gradlew tasks` — use `./gradlew :module:tasks --all`.

**Compile-check a change** (don't rebuild the app):

| Change in… | Command |
| :--- | :--- |
| common (default) | `:module:compileKotlinJvm` — commonMain + jvmMain |
| common, leanest | `:module:compileCommonMainKotlinMetadata` — skips platform `actual`s |
| Whole shared graph | `./gradlew :shared:compileKotlinJvm` (transitively compiles every module's commonMain) |

Modules use the new `com.android.kotlin.multiplatform.library` plugin, so per-module Android compile task names are non-standard — for a quick logic check prefer `compileKotlinJvm`; to validate the Android app specifically use `:androidApp:compileDebugSources`.

**Run / package:**

```bash
./gradlew :androidApp:installDebug          # install debug APK on a connected device/emulator
./gradlew :androidApp:assembleDebug         # build debug APK
./gradlew :androidApp:bundleRelease         # build release AAB (what CI ships to Play)
./gradlew :desktopApp:run                   # run the Desktop app (fastest test loop)
./gradlew :desktopApp:packageReleaseMsi     # Windows installer (macOS: packageReleaseDmg)
```

**Tests** — `commonTest` with `kotlin("test")`, run per target:

```bash
./gradlew :module:jvmTest                                  # JVM (any OS)
./gradlew :module:jvmTest --tests "...SomeTest"            # single class
./gradlew :module:allTests                                 # all targets
```

No detekt/ktlint/spotless — only Android Lint + the compiler. `.editorconfig` governs formatting.

**Resources** (Compose Resources) are generated automatically on compile; run manually only to refresh IDE accessors. Strings live in `common/ui/resources` (`values`, `values-ru`, `values-uk`) — **synced manually, no Lokalise**.

## Architecture

**Layered & modularized.** Modules depend strictly downward: `core → common → data → domain → ui → shared` (full list in `settings.gradle.kts`). Root package is `ua.isida.*`.

- **`core/*`** — foundational utils: `base`, `logging`, `preferences` (Multiplatform Settings).
- **`common/ui/*`** — UI-agnostic building blocks: design system (`compose`), `resources` (strings/images/fonts), `permissions` (Moko Permissions), `navigation`, `services`.
- **`data/*`** — `bluetooth` (device communication, the core integration), `models` (data models), `repos` (repository impls). *No GraphQL/Apollo; no SQLDelight today — persistence is via Multiplatform Settings in `core:preferences`.*
- **`domain`** — business logic / use cases.
- **`ui/*`** — feature modules: `devicemode`, `home/{base,prop,program,stats}`, `scan`, `logreport`, `properties`, `setprop`, `setday`. Each owns its screens, ViewModels, navigation.
- **`shared`** — umbrella that aggregates all features and assembles the DI graph; consumed by `androidApp` and `desktopApp`.

**Convention plugins (`gradle/build-logic`, namespace `ua.isida.*`):** `ua.isida.root`, `ua.isida.kotlin.multiplatform`, `ua.isida.android.application`, `ua.isida.android.library`, `ua.isida.compose`, `ua.isida.metro`. **New modules** mirror an existing one (e.g. `ui/scan/build.gradle.kts`): apply the relevant convention plugins, register in `settings.gradle.kts`, wire into `shared`. Inter-module deps use type-safe accessors (`projects.ui.scan`), never string paths.

**DI (Metro).** Root graph `shared/src/commonMain/kotlin/ua/isida/shared/di/AppGraph.kt` (+ `.android.kt` / `.jvm.kt` actuals). Dependencies are contributed from their own modules via Metro annotations, not registered centrally. ViewModels use the external **`metrox-viewmodel`** library (`libs.bundles.metrox.viewmodel`).

**Feature pattern (MVVM)** — canonical example `ui/scan` (`ScanDevicesViewModel.kt`, `ScanDevicesViewState.kt`, `Navigation.kt`, `ScanDevicesScreen.kt`):

- ViewModel = `androidx.lifecycle.ViewModel`, constructor-injected, contributed via:
  ```kotlin
  @ContributesIntoMap(ViewModelScope::class)
  @Inject @ViewModelKey
  class ScanDevicesViewModel(...) : ViewModel()
  ```
- State = `StateFlow<XViewState>` via `stateIn(viewModelScope, WhileSubscribed(5_000), initial)`; `XViewState` lives in a separate `*ViewState.kt`.
- Composables resolve the VM through the `metrox-viewmodel` injection helper.
- Navigation: **Navigation3** — each module exposes `fun EntryProviderScope<NavKey>.add<Feature>Screen(...)` with type-safe destinations (`entry<ScanDevicesScreen> { … }`); nav actions passed as lambdas.

**Design system.** Reusable Compose components carry the **`App`** prefix (`AppButton`, `AppTextField`, `AppDialog`, `AppTopAppBar`, …) in `common/ui/compose`. The theme entry point is `AppTheme { }`. (Do not reintroduce a `Wh*` prefix — it was legacy branding, since removed.)

## Conventions & gotchas
- **No hardcoded user-facing strings** — UI text goes in Compose Resources (`common/ui/resources`), with `ru`/`uk` translations.
- **Logging** — Kermit, lambda form: `logger.d { "…" }` (`Logger.withTag("Tag")`); the `add-logs` skill has the full conventions. Never log sensitive data.
- **Commit messages** — lowercase **Conventional Commits**: `type:` or `type(scope): summary`, where `type` ∈ `feat`, `fix`, `ui`, `perf`, `refactor`, `build`, `chore`, `docs`, `test`. Put `BREAKING CHANGE:` in the body for breaking changes. These prefixes drive the `release-notes` skill's categorization, so keep them accurate. No Linear/ticket prefixes.
- **Versioning** — `versionName` derives from `git describe --tags` and `versionCode` from `GITHUB_RUN_NUMBER` (`androidApp/build.gradle.kts`). Don't delete tags. A `vX.Y.Z` tag triggers the release workflows (Android → Google Play `internal` track + Firebase App Distribution; Desktop → GitHub Release installer).
- **Secrets** — `release.keystore`/`debug.keystore`, `local.properties`, and service-account JSONs are local/CI-only; never read or commit them (enforced in `.claude/settings.json` deny rules).
