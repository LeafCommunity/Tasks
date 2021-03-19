package community.leaf.tasks;

public interface TickSchedulable
{
    Concurrency getConcurrency();
    
    boolean isAsync();
    
    long getDelay();
    
    default boolean isDelayed() { return getDelay() > 0; }
    
    long getPeriod();
    
    default boolean isRecurring() { return getPeriod() > 0; }
    
    long getRepetitions();
    
    default boolean isRepeating() { return getRepetitions() > 0; }
}
