package community.leaf.tasks;

public interface TaskContext
{
    long getIterations();
    
    Repeats.Expected getExpectedRepetitions();
    
    boolean isCancelled();
    
    void cancel();
    
    Concurrency getConcurrency();
    
    default boolean isRepeatingForever() { return getExpectedRepetitions().until() == Repeats.FOREVER; }
    
    default boolean isRepeatingFinitely() { return getExpectedRepetitions().until() == Repeats.FINITE; }
    
    default boolean isRepeating() { return getExpectedRepetitions().until() != Repeats.NEVER; }
    
    default boolean isDoneRepeating() { return isCancelled() || getIterations() >= getExpectedRepetitions().repetitions(); }
    
    default boolean isFirstIteration() { return getIterations() == 0; }
    
    default boolean isLastIteration()
    {
        Repeats mode = getExpectedRepetitions().until();
        return (mode == Repeats.NEVER)
            || (mode == Repeats.FINITE && getIterations() == getExpectedRepetitions().repetitions());
    }
}
