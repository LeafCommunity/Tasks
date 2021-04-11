package community.leaf.tasks;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@SuppressWarnings("UnusedReturnValue")
public abstract class AbstractTaskBuilder<B extends AbstractTaskBuilder<B, P>, P extends PendingMilliseconds<B>> implements Schedulable
{
    protected final Concurrency concurrency;
    protected final TaskScheduler<?> scheduler;
    
    private long delay;
    private long period;
    private long repetitions;
    
    public AbstractTaskBuilder(Concurrency concurrency, TaskScheduler<?> scheduler)
    {
        this.concurrency = Objects.requireNonNull(concurrency, "concurrency");
        this.scheduler = Objects.requireNonNull(scheduler, "scheduler");
    }
    
    protected abstract P generatePendingMilliseconds(Function<Long, B> function, long units);
    
    @SuppressWarnings("unchecked")
    protected final B self() { return (B) this; }
    
    @Override
    public final Concurrency getConcurrency() { return concurrency; }
    
    @Override
    public long getDelay() { return delay; }
    
    public B delayByMilliseconds(long milliseconds)
    {
        delay = milliseconds;
        return self();
    }
    
    public B delay(long duration, TimeUnit unit)
    {
        return delayByMilliseconds(TimeUnit.MILLISECONDS.convert(duration, unit));
    }
    
    public P delay(long pendingDuration)
    {
        return generatePendingMilliseconds(this::delayByMilliseconds, pendingDuration);
    }
    
    @Override
    public long getPeriod() { return period; }
    
    public B everyFewMilliseconds(long milliseconds)
    {
        period = milliseconds;
        return self();
    }
    
    public B every(long duration, TimeUnit unit)
    {
        return everyFewMilliseconds(TimeUnit.MILLISECONDS.convert(duration, unit));
    }
    
    public P every(long pendingDuration)
    {
        return generatePendingMilliseconds(this::everyFewMilliseconds, pendingDuration);
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