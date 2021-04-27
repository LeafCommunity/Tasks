package community.leaf.tasks;

@SuppressWarnings("unused")
public interface TaskContext
{
    Concurrency concurrency();
    
    long iterations();
    
    Repeats.Expected repeats();
    
    boolean isCancelled();
    
    void cancel();
    
    default boolean isRepeatingForever() { return repeats().until() == Repeats.FOREVER; }
    
    default boolean isRepeatingFinitely() { return repeats().until() == Repeats.FINITE; }
    
    default boolean isRepeating() { return repeats().until() != Repeats.NEVER; }
    
    default boolean isDoneRepeating()
    {
        Repeats.Expected repeats = repeats();
        return (isCancelled())
            || (repeats.until() == Repeats.NEVER && iterations() > repeats.repetitions())
            || (repeats.until() == Repeats.FINITE && iterations() >= repeats.repetitions());
    }
    
    default boolean isFirstIteration() { return iterations() == 0; }
    
    default boolean isLastIteration()
    {
        Repeats.Expected repeats = repeats();
        return (repeats.until() == Repeats.NEVER)
            || (repeats.until() == Repeats.FINITE && iterations() == repeats.repetitions() - 1);
    }
}
