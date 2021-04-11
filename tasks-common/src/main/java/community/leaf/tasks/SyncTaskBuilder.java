package community.leaf.tasks;

public class SyncTaskBuilder extends TaskBuilder<SyncTaskBuilder>
{
    public SyncTaskBuilder(TaskScheduler<?> scheduler) { super(scheduler); }
    
    @Override
    public final Concurrency getConcurrency() { return Concurrency.SYNC; }
}
