package io.kestra.plugin.skopeo.cli;

import java.util.List;

import io.kestra.core.exceptions.IllegalVariableEvaluationException;
import io.kestra.core.models.annotations.Example;
import io.kestra.core.models.annotations.Plugin;
import io.kestra.core.models.property.Property;
import io.kestra.core.models.tasks.RunnableTask;
import io.kestra.core.runners.RunContext;
import io.kestra.plugin.scripts.exec.AbstractExecScript;
import io.kestra.plugin.scripts.exec.scripts.models.DockerOptions;
import io.kestra.plugin.scripts.exec.scripts.models.ScriptOutput;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@NoArgsConstructor
@Schema(
    title = "Execute Skopeo CLI commands in Docker",
    description = """
        Runs the provided Skopeo CLI commands via the script runner. Defaults to Docker image `quay.io/skopeo/stable`
        when no image override is set. Use this task to copy, inspect, delete, and manage container images
        across registries without requiring a local Docker daemon.
        """
)
@Plugin(
    examples = {
        @Example(
            full = true,
            title = "Inspect a public container image using Skopeo.",
            code = """
                id: skopeo_inspect_image
                namespace: company.team

                tasks:
                  - id: skopeo_inspect
                    type: io.kestra.plugin.skopeo.cli.SkopeoCLI
                    commands:
                      - skopeo inspect docker://alpine:latest
                """
        ),
        @Example(
            full = true,
            title = "Copy a container image between transports.",
            code = """
                id: skopeo_copy_image
                namespace: company.team

                tasks:
                  - id: skopeo_copy
                    type: io.kestra.plugin.skopeo.cli.SkopeoCLI
                    commands:
                      - skopeo copy docker://alpine:latest oci:/tmp/alpine:latest
                """
        ),
        @Example(
            full = true,
            title = "List all tags for a container image repository.",
            code = """
                id: skopeo_list_tags
                namespace: company.team

                tasks:
                  - id: skopeo_tags
                    type: io.kestra.plugin.skopeo.cli.SkopeoCLI
                    commands:
                      - skopeo list-tags docker://library/alpine
                """
        )
    }
)
public class SkopeoCLI extends AbstractExecScript implements RunnableTask<ScriptOutput> {
    private static final String DEFAULT_IMAGE = "quay.io/skopeo/stable";

    @Builder.Default
    protected Property<String> containerImage = Property.ofValue(DEFAULT_IMAGE);

    @Schema(
        title = "Skopeo CLI commands to execute",
        description = "Commands are executed in order by the task runner; provide full Skopeo subcommands such as `skopeo inspect ...` or `skopeo copy ...`."
    )
    @NotNull
    protected Property<List<String>> commands;

    @Override
    protected DockerOptions injectDefaults(RunContext runContext, DockerOptions original) throws IllegalVariableEvaluationException {
        var builder = original.toBuilder();
        if (original.getImage() == null) {
            builder.image(runContext.render(this.getContainerImage()).as(String.class).orElse(null));
        }
        return builder.build();
    }

    @Override
    public ScriptOutput run(RunContext runContext) throws Exception {
        return this.commands(runContext)
            .withInterpreter(this.interpreter)
            .withBeforeCommands(beforeCommands)
            .withBeforeCommandsWithOptions(true)
            .withCommands(commands)
            .run();
    }
}
