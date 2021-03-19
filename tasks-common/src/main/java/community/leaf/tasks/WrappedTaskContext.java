package community.leaf.tasks;

import pl.tlinkowski.annotation.basic.NullOr;

import java.util.Objects;

public abstract class WrappedTaskContext<T> implements TaskContext
{
    private long iterations = 0;
    private @NullOr T wrapped = null;
    
    private final long expectedIterations;
    
    public WrappedTaskContext(long repetitions)
    {
        // (-Inf, -1] => forever
        // [0, 1]     => once
        // (1, Inf)   => finite
        this.expectedIterations = (repetitions <= -1) ? -1 : (repetitions == 0) ? 1 : repetitions;
    }
    
    @SuppressWarnings("ConstantConditions")
    public T getTask() { return Objects.requireNonNull(wrapped, "wrapped task not initialized"); }
    
    public void wrap(T unwrapped) { this.wrapped = unwrapped; }
    
    @Override
    public long getIterations() { return iterations; }
    
    public void iterate() { iterations += 1; }
    
    @Override
    public long getExpectedIterations() { return expectedIterations; }
    
    @Override
    public boolean isRepeatingForever() { return expectedIterations <= -1; }
    
    @Override
    public boolean isRepeatingFinitely() { return expectedIterations > 1; }
    
    @Override
    public boolean isDoneRepeating() { return isCancelled() || iterations >= expectedIterations; }
}
