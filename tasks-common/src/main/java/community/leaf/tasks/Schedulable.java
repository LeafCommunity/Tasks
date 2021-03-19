package community.leaf.tasks;

public interface Schedulable
{
    Concurrency getConcurrency();
    
    boolean isAsync();
    
    long getDelay();
    
    default boolean isDelayed() { return getDelay() > 0; }
    
    long getPeriod();
    
    default boolean isRecurring() { return getPeriod() > 0; }
}
