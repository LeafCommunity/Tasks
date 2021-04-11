package community.leaf.tasks;

public class AsyncTaskBuilder extends TaskBuilder<AsyncTaskBuilder>
{
    public AsyncTaskBuilder(TaskScheduler<?> scheduler) { super(scheduler); }
    
    @Override
    public final Concurrency getConcurrency() { return Concurrency.ASYNC; }
}
