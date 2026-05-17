# AGENTS.md

Guidance for future agents working in this repository.

## Project Overview

`lanjoni.dev` is a static personal blog and markdown article site.

- Stack: ClojureScript, Helix, Flex, Reitit, Shadow CLJS, React, Tailwind CSS, DaisyUI, react-markdown, remark-gfm.
- No backend. Static assets are served from `public`.
- App entrypoint: `src/dev/lanjoni/core.cljs`.
- Routes: `src/dev/lanjoni/routes.cljs`.
- Main app shell: `src/dev/lanjoni/panels/shell/view.cljs`.
- Markdown articles live in `public/md`.
- Global CSS and Tailwind setup live in `public/css/index.css`.

## Working Principles

- Think before coding. State assumptions and surface ambiguity before making changes.
- Prefer the simplest change that satisfies the request.
- Keep changes surgical. Touch only files directly related to the task.
- Match the existing style, even when another style might be reasonable.
- Do not refactor, reorganize, or reformat adjacent code unless the task requires it.
- Do not add speculative features, configurability, or abstractions.
- If a task is only asking to understand or explain code, do not edit files unless asked.
- If something is unclear enough that a reasonable assumption would be risky, ask first.

## Commands

Use these commands from the repository root:

- Install dependencies: `npm install`
- Start the dev server: `npm run dev`
- Build release assets: `npm run release`
- Run CI tests: `npm run ci-tests`

Local development URLs after `npm run dev`:

- App: `http://localhost:5000`
- Browser test output: `http://localhost:5001`
- Shadow CLJS tools: `http://localhost:5002`

Do not run tests unless the user explicitly asks. In particular, do not run `npm run ci-tests` proactively. Do not run `lein lint` or `lein lint-fix`.

## Repository Structure

- `src/dev/lanjoni/components`: shared UI components.
- `src/dev/lanjoni/panels`: page-level views and panel-specific components/state.
- `src/dev/lanjoni/infra`: wrappers and integration code for Helix, Flex, routing, HTTP, and user config.
- `test/dev/lanjoni`: CLJS tests mirroring source namespaces.
- `test/dev/lanjoni/aux/testing_library.cljs`: Testing Library helpers.
- `public/index.html`: static HTML shell.
- `public/css/index.css`: Tailwind v4 imports, DaisyUI plugin config, theme tokens, and global styles.
- `public/md`: markdown article content.

The README mentions `tailwind.config.js`, but this repo currently uses Tailwind v4 configuration in `public/css/index.css`.

## ClojureScript Style

- Use namespace `dev.lanjoni...` matching the file path.
- Use the local `defnc` wrapper from `dev.lanjoni.infra.helix`.
- Prefer `helix.dom :as d` for DOM elements.
- Use `helix.core/$` for components.
- Keep props destructuring consistent with existing code:
  - Components usually accept one props map.
  - If destructuring namespaced keys with `:keys`, use the unqualified local symbol in the body.
- Do not add comments inside Clojure/ClojureScript code unless explicitly requested.
- Avoid changing functions unrelated to the request.
- Remove only unused imports/vars introduced by your own changes.

## UI And Styling

- Use Tailwind utility classes inline via `:className`, following existing patterns.
- Use the existing theme tokens where applicable:
  - `purple` from `--color-purple`
  - `gray` from `--color-gray`
- Keep the existing minimalist personal-site style.
- Do not introduce a new design system or broad visual refresh unless asked.
- Keep static images under `public/images` and reference them with absolute public paths such as `/images/...`.

## Routing And Content

- Routes are defined in `src/dev/lanjoni/routes.cljs`.
- Routing uses Reitit frontend with fragment URLs.
- Article pages are available at `/#/writing/:content-name`.
- Article markdown is fetched from `/md/:content-name.md`.
- When adding an article, add the markdown file under `public/md`; only update routes/views if navigation or listing behavior must change.

## State And Effects

- Flex sources/signals live near the feature that owns the state.
- Use `flex/source`, `flex/signal`, and `flex/batch` consistently with existing state files.
- Keep async fetch behavior small and feature-local.
- `content-fetch` guards stale responses with `request-seq`; preserve that behavior when touching article loading.

## Tests

- Tests use `cljs.test`, `promesa`, `matcher-combinators`, and React Testing Library.
- Test files mirror source namespaces and use `-test` suffixes.
- Existing tests commonly use `async`, `p/let`, `p/then`, and `tl/render`.
- Add or update focused tests when the user asks for tests or when the requested change explicitly requires test coverage.
- Do not run tests unless the user asks.

## Dependency Changes

- JavaScript dependencies belong in `package.json` and `package-lock.json`.
- Clojure/ClojureScript dependencies belong in `deps.edn`.
- Avoid adding dependencies for small changes.
- If a dependency is necessary, explain why before adding it.

## Git And Commits

- Before editing, check the working tree and do not overwrite unrelated user changes.
- Never use destructive git commands unless explicitly requested.
- Commit messages must use Conventional Commits:
  - Format: `<type>[optional scope]: <description>`
  - Types: `feat`, `fix`, `refactor`, `style`, `docs`, `test`, `chore`
  - Subject max 50 characters, imperative mood, no trailing period.
- Do not add `Co-Authored-By`, `Generated with Claude Code`, or similar generator lines.

## Verification

For code changes, define a concrete verification path before editing. Because tests should not be run unless requested, use the lightest safe verification available by default:

- Inspect affected namespaces for syntax/import consistency.
- Check changed files with targeted reads.
- Mention tests that were not run.
- If the user explicitly asks to run tests, use `npm run ci-tests` for the full suite.

