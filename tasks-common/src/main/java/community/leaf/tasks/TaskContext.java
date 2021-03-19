package community.leaf.tasks;

public interface TaskContext
{
    long getIterations();
    
    boolean isCancelled();
    
    void cancel();
    
    Concurrency getConcurrency();
    
    long getExpectedIterations();
    
    boolean isRepeatingForever();
    
    boolean isRepeatingFinitely();
    
    boolean isDoneRepeating();
    
    default boolean isRepeating()
    {
        return isRepeatingForever() || isRepeatingFinitely();
    }
    
    default boolean isFirstIteration()
    {
        return getIterations() == 0;
    }
    
    default boolean isLastIteration()
    {
        return isRepeatingFinitely() && getIterations() == getExpectedIterations() - 1;
    }
}
