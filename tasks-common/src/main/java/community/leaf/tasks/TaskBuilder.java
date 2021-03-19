package community.leaf.tasks;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("UnusedReturnValue")
public abstract class TaskBuilder<B extends TaskBuilder<B>> implements TickSchedulable
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
    
    public B delay(long duration, TimeUnit unit)
    {
        return delay(Ticks.from(duration, unit));
    }
    
    @Override
    public long getPeriod() { return period; }
    
    public B every(long ticks)
    {
        period = ticks;
        return self();
    }
    
    public B every(long duration, TimeUnit unit)
    {
        return every(Ticks.from(duration, unit));
    }
    
    @Override
    public long getRepetitions() { return repetitions; }
    
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
                if (task.isDoneRepeating()) { task.cancel(); }
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
                if (task.isDoneRepeating()) { task.cancel(); }
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
