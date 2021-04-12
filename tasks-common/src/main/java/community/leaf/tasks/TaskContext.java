package community.leaf.tasks;

@SuppressWarnings("unused")
public interface TaskContext
{
    Concurrency getConcurrency();
    
    long getIterations();
    
    Repeats.Expected getExpectedRepetitions();
    
    boolean isCancelled();
    
    void cancel();
    
    default boolean isRepeatingForever() { return getExpectedRepetitions().until() == Repeats.FOREVER; }
    
    default boolean isRepeatingFinitely() { return getExpectedRepetitions().until() == Repeats.FINITE; }
    
    default boolean isRepeating() { return getExpectedRepetitions().until() != Repeats.NEVER; }
    
    default boolean isDoneRepeating()
    {
        Repeats.Expected expected = getExpectedRepetitions();
        return (isCancelled())
            || (expected.until() == Repeats.NEVER && getIterations() > expected.repetitions())
            || (expected.until() == Repeats.FINITE && getIterations() >= expected.repetitions());
    }
    
    default boolean isFirstIteration() { return getIterations() == 0; }
    
    default boolean isLastIteration()
    {
        Repeats.Expected expected = getExpectedRepetitions();
        return (expected.until() == Repeats.NEVER)
            || (expected.until() == Repeats.FINITE && getIterations() == expected.repetitions() - 1);
    }
}
