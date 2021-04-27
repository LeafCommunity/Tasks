package community.leaf.tasks;

public interface Schedulable
{
    Concurrency concurrency();
    
    long delay();
    
    long period();
    
    Repeats.Expected repeats();
    
    default boolean isDelayed() { return delay() > 0; }
}
