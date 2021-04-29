package community.leaf.tasks;

import pl.tlinkowski.annotation.basic.NullOr;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

public abstract class AbstractTaskContext<T> implements TaskContext<T>
{
    private final AtomicLong iterations = new AtomicLong();
    private final AtomicReference<@NullOr T> wrapped = new AtomicReference<>();
    
    private final Schedule schedule;
    
    protected AbstractTaskContext(Schedule schedule)
    {
        this.schedule = Objects.requireNonNull(schedule, "schedule");
    }
    
    @Override
    public final long iterations() { return iterations.get(); }
    
    @Override
    public final void iterate() { iterations.incrementAndGet(); }
    
    @Override
    public final Schedule schedule() { return schedule; }
    
    @SuppressWarnings("NullableProblems") // no problems...
    @Override
    public final T task()
    {
        @NullOr T task = wrapped.get();
        if (task != null) { return task; }
        throw new IllegalStateException("No task wrapped yet");
    }
    
    @Override
    public final void task(T task)
    {
        // Expecting existing to be null (should not be initialized yet).
        if (wrapped.compareAndSet(null, task)) { return; }
        throw new IllegalStateException("Already contains a wrapped task");
    }
}
