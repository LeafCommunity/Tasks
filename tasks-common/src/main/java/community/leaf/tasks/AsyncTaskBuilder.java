package community.leaf.tasks;

public class AsyncTaskBuilder extends TaskBuilder<AsyncTaskBuilder>
{
    public AsyncTaskBuilder(TaskScheduler<?> scheduler) { super(scheduler); }
    
    @Override
    public Concurrency getConcurrency() { return Concurrency.ASYNC; }
    
    @Override
    public final boolean isAsync() { return true; }
}
