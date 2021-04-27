package community.leaf.examples.tasks.executors;

import community.leaf.tasks.Concurrency;

public class ExampleMain
{
    public static void main(String[] args)
    {
        ExampleTasks tasks = new ExampleTasks();
        
        tasks.async().run(() -> System.out.println("Started!"));
        
        tasks.async().delay(2).seconds().run(() -> System.out.println("Wow! (2 seconds later)"));
        
        tasks.async().every(500).milliseconds().forever().runWithContext(task ->
            System.out.println("Twice a second #" + task.iterations())
        );
        
        // 10 seconds of execution...
        try { Thread.sleep(10000); }
        catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        
        tasks.executor(Concurrency.ASYNC).shutdown();
        System.out.println("Goodbye.");
    }
}
