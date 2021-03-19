package community.leaf.tasks;

public interface TaskContext
{
    long getIterations();
    
    boolean isCancelled();
    
    void cancel();
    
    Concurrency getConcurrency();
}
