# Kestra Skopeo Plugin

## What

- Provides plugin components under `io.kestra.plugin.skopeo.cli`.
- Includes classes such as `SkopeoCLI`.

## Why

- What user problem does this solve? Teams need to plugin for interacting with container images and registries using Skopeo from orchestrated workflows instead of relying on manual console work, ad hoc scripts, or disconnected schedulers.
- Why would a team adopt this plugin in a workflow? It keeps Skopeo steps in the same Kestra flow as upstream preparation, approvals, retries, notifications, and downstream systems.
- What operational/business outcome does it enable? It reduces manual handoffs and fragmented tooling while improving reliability, traceability, and delivery speed for processes that depend on Skopeo.

## How

### Architecture

Single-module plugin. Source packages under `io.kestra.plugin`:

- `skopeo`

### Key Plugin Classes

- `io.kestra.plugin.skopeo.cli.SkopeoCLI`

### Project Structure

```
plugin-skopeo/
├── src/main/java/io/kestra/plugin/skopeo/cli/
├── src/test/java/io/kestra/plugin/skopeo/
├── build.gradle
└── README.md
```

## Local rules

- Base the wording on the implemented packages and classes, not on template README text.

## References

- https://kestra.io/docs/plugin-developer-guide
- https://kestra.io/docs/plugin-developer-guide/contribution-guidelines
