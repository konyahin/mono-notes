# ktor-spa-template

Template for a small single-user PWA: a Ktor backend serving a Svelte SPA, SQLite for storage.

## Stack

- Backend — Kotlin, Ktor, SQLite
- Frontend — Svelte 5, TypeScript, Vite, Pico.css
- Build — Gradle (Kotlin DSL) drives both sides via gradle-node-plugin

## Requirements

- JDK 21 (auto-provisioned by the Foojay toolchain resolver)
- Node.js 20+ on `PATH`

## Run

```sh
./gradlew run              # build frontend, start server on :8080
./gradlew build            # full build, runs tests
./gradlew shadowJar        # fat jar in build/libs/, run with `java -jar ...`
```

For frontend development with hot reload, run backend and Vite in parallel:

```sh
./gradlew run              # backend on :8080
cd frontend && npm run dev # Vite on :5173, proxies /api to :8080
```

## Make it your own

1. Rename the project. Update `rootProject.name` in `settings.gradle.kts` and `group` in `build.gradle.kts`. Rename the Kotlin package `xyz.konyahin` (sources under `src/main/kotlin/`) and update the `modules:` paths in `src/main/resources/application.yaml`.
1. Update the PWA manifest. Change `name`, `short_name`, `theme_color`, `background_color` in `frontend/vite.config.ts`. Replace `frontend/public/icon-192.png` and `icon-512.png` with your icons.
1. Set the page title in `frontend/index.html`.
1. Replace the example feature. `Greetings` is a working CRUD demo across one table, one route, and two Svelte components (`AddGreetings`, `GreetingsList`). Delete them and add your own table in `src/main/kotlin/model/`, route in `Routing.kt`, components in `frontend/src/lib/`.
1. Drop the dev database. `rm data.db` so your new schema gets created on first start.
1. Adjust the database path. Change `db.url` in `application.yaml` if you want SQLite somewhere outside the working directory.
