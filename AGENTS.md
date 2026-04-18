# Kestra Skopeo Plugin

## What

- Provides plugin components under `io.kestra.plugin.skopeo.cli`.
- Includes classes such as `SkopeoCLI`.

## Why

- This plugin integrates Kestra with Skopeo.
- It provides plugin for interacting with container images and registries using Skopeo.

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
