package io.kestra.plugin.skopeo;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableMap;

import io.kestra.core.junit.annotations.KestraTest;
import io.kestra.core.models.executions.LogEntry;
import io.kestra.core.models.property.Property;
import io.kestra.core.queues.QueueFactoryInterface;
import io.kestra.core.queues.QueueInterface;
import io.kestra.core.runners.RunContext;
import io.kestra.core.runners.RunContextFactory;
import io.kestra.core.utils.TestsUtils;
import io.kestra.plugin.scripts.exec.scripts.models.ScriptOutput;
import io.kestra.plugin.skopeo.cli.SkopeoCLI;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import reactor.core.publisher.Flux;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@KestraTest
public class SkopeoCLITest {
    @Inject
    RunContextFactory runContextFactory;

    @Inject
    @Named(QueueFactoryInterface.WORKERTASKLOG_NAMED)
    private QueueInterface<LogEntry> logQueue;

    @Test
    void skopeoInspectImage() throws Exception {
        List<LogEntry> logs = new CopyOnWriteArrayList<>();
        Flux<LogEntry> receive = TestsUtils.receive(logQueue, l -> logs.add(l.getLeft()));

        var skopeoTask = SkopeoCLI.builder()
            .id(SkopeoCLI.class.getSimpleName())
            .type(SkopeoCLI.class.getName())
            .commands(
                Property.ofValue(
                    List.of(
                        "skopeo inspect docker://alpine:latest"
                    )
                )
            )
            .build();

        RunContext runContext = TestsUtils.mockRunContext(runContextFactory, skopeoTask, ImmutableMap.of());
        ScriptOutput run = skopeoTask.run(runContext);

        assertThat(run.getExitCode(), is(0));

        TestsUtils.awaitLog(logs, log -> log.getMessage() != null && log.getMessage().toLowerCase().contains("alpine"));
        receive.blockLast();
        assertThat(List.copyOf(logs).stream().anyMatch(log -> log.getMessage() != null && log.getMessage().toLowerCase().contains("alpine")), is(true));
    }
}
