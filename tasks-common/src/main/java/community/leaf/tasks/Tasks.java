package community.leaf.tasks;

public class Tasks
{
    private Tasks() { throw new UnsupportedOperationException(); }
    
    public static SyncTaskBuilder sync(TaskScheduler<?> scheduler)
    {
        return new SyncTaskBuilder(scheduler);
    }
    
    public static SyncTaskBuilder sync(TaskSource source)
    {
        return sync(source.getTaskScheduler());
    }
    
    public static AsyncTaskBuilder async(TaskScheduler<?> scheduler)
    {
        return new AsyncTaskBuilder(scheduler);
    }
    
    public static AsyncTaskBuilder async(TaskSource source)
    {
        return async(source.getTaskScheduler());
    }
}
