package community.leaf.tasks;

import pl.tlinkowski.annotation.basic.NullOr;

import java.util.Objects;

public abstract class WrappedTaskContext<T> implements TaskContext
{
    private long iterations = 0;
    private @NullOr T wrapped = null;
    
    private final Repeats.Expected repetitions;
    
    public WrappedTaskContext(Repeats.Expected repetitions)
    {
        this.repetitions = Objects.requireNonNull(repetitions, "repetitions");
    }
    
    @Override
    public final long getIterations() { return this.iterations; }
    
    public final void iterate() { this.iterations += 1; }
    
    @Override
    public final Repeats.Expected getExpectedRepetitions() { return repetitions; }
    
    public final T getTask()
    {
        if (this.wrapped != null) { return this.wrapped; }
        throw new IllegalStateException("No task wrapped yet.");
    }
    
    public final T wrap(T unwrapped)
    {
        if (this.wrapped == null) { return this.wrapped = Objects.requireNonNull(unwrapped, "unwrapped"); }
        throw new IllegalStateException("Already contains a wrapped task.");
    }
}
