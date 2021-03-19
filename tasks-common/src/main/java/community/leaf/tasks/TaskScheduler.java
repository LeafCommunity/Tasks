package community.leaf.tasks;

@SuppressWarnings("UnusedReturnValue")
public interface TaskScheduler<T>
{
    WrappedTaskContext<T> createTaskContext();
    
    T runTask(Concurrency concurrency, Runnable runnable);
    
    T runFutureTask(Concurrency concurrency, Runnable runnable, long delay);
    
    T runRecurringTask(Concurrency concurrency, Runnable runnable, long delay, long period);
    
    default T schedule(Schedulable when, Runnable runnable)
    {
        if (when.isRecurring())
        {
            return runRecurringTask(when.getConcurrency(), runnable, when.getDelay(), when.getPeriod());
        }
        else if (when.isDelayed())
        {
            return runFutureTask(when.getConcurrency(), runnable, when.getDelay());
        }
        else
        {
            return runTask(when.getConcurrency(), runnable);
        }
    }
    
    default T schedule(Schedulable when, ContextualRunnable runnable)
    {
        WrappedTaskContext<T> context = createTaskContext();
        
        T task = schedule(when, () -> {
            runnable.run(context);
            context.incrementIterations();
        });
        
        context.wrap(task);
        return task;
    }
}
