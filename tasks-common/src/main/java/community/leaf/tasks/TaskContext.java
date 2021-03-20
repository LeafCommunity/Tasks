package community.leaf.tasks;

public interface TaskContext
{
    long getIterations();
    
    long getExpectedIterations();
    
    boolean isCancelled();
    
    void cancel();
    
    Concurrency getConcurrency();
    
    default boolean isRepeatingForever() { return getExpectedIterations() <= -1; }
    
    default boolean isRepeatingFinitely() { return getExpectedIterations() > 1; }
    
    default boolean isRepeating() { return isRepeatingForever() || isRepeatingFinitely(); }
    
    default boolean isDoneRepeating() { return isCancelled() || getIterations() >= getExpectedIterations(); }
    
    default boolean isFirstIteration() { return getIterations() == 0; }
    
    default boolean isLastIteration() { return isRepeatingFinitely() && getIterations() == getExpectedIterations() - 1; }
}
