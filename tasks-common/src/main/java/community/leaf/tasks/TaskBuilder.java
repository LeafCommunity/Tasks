package community.leaf.tasks;

import java.util.Objects;

@SuppressWarnings("UnusedReturnValue")
public abstract class TaskBuilder<B extends TaskBuilder<B>> implements Schedulable
{
    protected final TaskScheduler<?> scheduler;
    
    private long delay;
    private long period;
    private long repetitions;
    
    public TaskBuilder(TaskScheduler<?> scheduler)
    {
        this.scheduler = Objects.requireNonNull(scheduler, "scheduler");
    }
    
    @SuppressWarnings("unchecked")
    protected final B self()
    {
        return (B) this;
    }
    
    @Override
    public long getDelay() { return delay; }
    
    public B delay(long ticks)
    {
        delay = ticks;
        return self();
    }
    
    @Override
    public long getPeriod() { return period; }
    
    public B every(long ticks)
    {
        period = ticks;
        return self();
    }
    
    public B repeat(long iterations)
    {
        repetitions = iterations;
        return self();
    }
    
    public <R extends Runnable> R run(R runnable)
    {
        Objects.requireNonNull(runnable, "runnable");
        
        if (repetitions > 0)
        {
            scheduler.schedule(this, task -> {
                if (task.getIterations() >= repetitions) { task.cancel(); }
                else { runnable.run(); }
            });
        }
        else
        {
            scheduler.schedule(this, runnable);
        }
        return runnable;
    }
    
    public <R extends ContextualRunnable> R runWithContext(R runnable)
    {
        Objects.requireNonNull(runnable, "runnable");
        
        if (repetitions > 0)
        {
            scheduler.schedule(this, task -> {
                if (task.getIterations() >= repetitions) { task.cancel(); }
                else { runnable.run(task); }
            });
        }
        else
        {
            scheduler.schedule(this, runnable);
        }
        return runnable;
    }
}
