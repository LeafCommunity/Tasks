package community.leaf.tasks;

@FunctionalInterface
public interface TaskSource
{
    TaskScheduler<?> getTaskScheduler();
    
    default SyncTaskBuilder sync() { return new SyncTaskBuilder(getTaskScheduler()); }
    
    default AsyncTaskBuilder async() { return new AsyncTaskBuilder(getTaskScheduler()); }
}
