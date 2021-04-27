package community.leaf.examples.tasks.executors;

import community.leaf.tasks.Concurrency;
import community.leaf.tasks.executors.AsyncExecutorTaskSource;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class ExampleTasks implements AsyncExecutorTaskSource
{
    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    
    @Override
    public ScheduledExecutorService executor(Concurrency concurrency)
    {
        if (concurrency == Concurrency.ASYNC) { return executorService; }
        throw new IllegalArgumentException("Unsupported concurrency: " + concurrency);
    }
}
