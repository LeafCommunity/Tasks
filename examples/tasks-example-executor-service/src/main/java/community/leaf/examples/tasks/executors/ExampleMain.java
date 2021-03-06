package community.leaf.examples.tasks.executors;

import community.leaf.tasks.Concurrency;
import community.leaf.tasks.executors.ExecutorTaskSource;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class ExampleMain
{
    public static void main(String[] args)
    {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        
        ExecutorTaskSource.Async tasks = (concurrency) -> {
            if (concurrency == Concurrency.ASYNC) { return executorService; }
            throw new IllegalArgumentException("Unsupported concurrency: " + concurrency);
        };
        
        tasks.async().run(() -> System.out.println("Started!"));
        
        tasks.async().delay(2).seconds().run(() -> System.out.println("Wow! (2 seconds later)"));
        
        tasks.async().every(500).milliseconds().forever().run(task ->
            System.out.println("Twice a second #" + task.iterations())
        );
        
        // 10 seconds of execution...
        try { Thread.sleep(10000); }
        catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        
        tasks.executor(Concurrency.ASYNC).shutdown();
        System.out.println("Goodbye.");
    }
}
