package community.leaf.tasks;

public interface Schedulable
{
    Concurrency getConcurrency();
    
    long getDelay();
    
    default boolean isDelayed() { return getDelay() > 0; }
    
    long getPeriod();
    
    Repeats.Expected getRepetitions();
}
