# Kestra Skopeo Plugin

## What

Plugin for interacting with container images and registries using skopeo. Exposes 1 plugin component: SkopeoCLI task.

## Why

Enables Kestra workflows to copy, inspect, delete, and manage container images across registries without requiring a local Docker daemon.

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

### Important Commands

```bash
# Build the plugin
./gradlew shadowJar

# Run tests
./gradlew test

# Build without tests
./gradlew shadowJar -x test
```

### Configuration

All tasks and triggers accept standard Kestra plugin properties. Credentials should use
`{{ secret('SECRET_NAME') }}` — never hardcode real values.

## Agents

**IMPORTANT:** This is a Kestra plugin repository (prefixed by `plugin-`, `storage-`, or `secret-`). You **MUST** delegate all coding tasks to the `kestra-plugin-developer` agent. Do NOT implement code changes directly — always use this agent.
