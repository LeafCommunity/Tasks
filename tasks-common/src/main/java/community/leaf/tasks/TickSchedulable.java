package community.leaf.tasks;

public interface TickSchedulable
{
    Concurrency getConcurrency();
    
    long getDelay();
    
    default boolean isDelayed() { return getDelay() > 0; }
    
    long getPeriod();
    
    default boolean isRepeating() { return getPeriod() > 0; }
    
    long getRepetitions();
    
    default boolean hasFiniteRepetitions() { return getRepetitions() > 0; }
}
