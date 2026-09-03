# How to use the Skopeo plugin

Run Skopeo commands to inspect, copy, and manage container images across registries from Kestra flows.

## Authentication

Skopeo has no separate connection class — authenticate by passing credentials directly in `commands` using Skopeo's `--src-creds user:pass`, `--dest-creds user:pass`, or `--creds user:pass` flags. Use [secrets](https://kestra.io/docs/concepts/secret) to inject credentials safely, for example `{{ secret('REGISTRY_USER') }}:{{ secret('REGISTRY_PASSWORD') }}`. For unauthenticated sources or destinations use `--src-no-creds` or `--dest-no-creds`.

## Tasks

`cli.SkopeoCLI` runs one or more Skopeo commands inside a container — set `commands` (required list of full Skopeo subcommands such as `skopeo inspect docker://alpine:latest` or `skopeo copy docker://src docker://dest`). The default container image is `quay.io/skopeo/stable`; override `containerImage` to pin a specific version. Use `beforeCommands` to install extra tools or set environment variables before the main commands run. Set runner configuration on each task.
