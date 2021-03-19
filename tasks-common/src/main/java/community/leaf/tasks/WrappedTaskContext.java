package community.leaf.tasks;

import pl.tlinkowski.annotation.basic.NullOr;

import java.util.Objects;

public abstract class WrappedTaskContext<T> implements TaskContext
{
    private long iterations = 0;
    
    private @NullOr T wrapped;
    
    @SuppressWarnings("ConstantConditions")
    public T getTask() { return Objects.requireNonNull(wrapped, "wrapped task not initialized"); }
    
    public void wrap(T unwrapped) { this.wrapped = unwrapped; }
    
    @Override
    public long getIterations() { return iterations; }
    
    public void incrementIterations() { iterations += 1; }
}
