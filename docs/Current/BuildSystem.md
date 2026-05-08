# Build System

The project uses **Gradle** with the **Kotlin DSL** (`build.gradle.kts`). Key choices and rationale:

## Plugins

- `java` – standard Java compilation.
- `application` – defines the main class for the CLI.
- `com.gradleup.shadow` (v9.4.1) – creates fat JARs with all dependencies.

## Main Classes

- **CLI**: `Main` (set via `application.mainClass`).
- **GUI**: `ClubManagementGUI` (run with custom task `runGUI`).

## Dependency

- `org.xerial:sqlite-jdbc:3.51.0.0` – SQLite JDBC driver. Only runtime dependency needed.

## Source Sets

- Java sources are located directly under `src/` (not `src/main/java`), configured by `sourceSets.main.java.setSrcDirs(["src"])`.
- Resources (including SQL files in `src/db/`) are also served from `src/`.

## Custom Tasks

1. **`run`** – Runs the CLI with `System.in` connected (for interactive input).
2. **`runGUI`** – Runs the GUI with `mainClass.set("ClubManagementGUI")`.
3. **`shadowJar`** – Builds a fat JAR named `Project-cli.jar` with `Main` as entry point.
4. **`shadowJarGUI`** – Builds a fat JAR named `Project-gui.jar` with `ClubManagementGUI` as entry point.

## Why Kotlin DSL?

- Type‑safe build scripts, better IDE support (auto‑completion, refactoring).
- More concise than Groovy DSL.
- The team can still use Java entirely; the build file is the only Kotlin part.

## Nix Shell (shell.nix)

- Provides a reproducible development environment with JDK 21 and Gradle.
- Sets `JAVA_HOME` and makes helper scripts executable.
- Optional; not required for standard builds.
