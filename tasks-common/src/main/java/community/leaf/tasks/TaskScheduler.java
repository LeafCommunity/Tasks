package community.leaf.tasks;

@SuppressWarnings("UnusedReturnValue")
public interface TaskScheduler<T>
{
    WrappedTaskContext<T> createTaskContext(Repeats.Expected repetitions);
    
    T runTask(Concurrency concurrency, Runnable runnable);
    
    T runFutureTask(Concurrency concurrency, Runnable runnable, long delay);
    
    T runRepeatingTask(Concurrency concurrency, Runnable runnable, long delay, long period);
    
    default T schedule(Schedulable when, Runnable runnable)
    {
        if (when.getRepetitions().until() != Repeats.NEVER)
        {
            return runRepeatingTask(when.getConcurrency(), runnable, when.getDelay(), when.getPeriod());
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
        WrappedTaskContext<T> context = createTaskContext(when.getRepetitions());
        
        T task = schedule(when, () -> {
            runnable.run(context);
            context.iterate();
        });
        
        return context.wrap(task);
    }
}
