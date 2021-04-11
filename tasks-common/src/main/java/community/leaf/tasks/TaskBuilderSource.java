package community.leaf.tasks;

public interface TaskBuilderSource<B extends AbstractTaskBuilder<B, ? extends PendingMilliseconds<B>>>
{
    TaskScheduler<?> getTaskScheduler();
    
    TaskBuilderConstructor<B> getTaskBuilderConstructor();
    
    default B sync()
    {
        return getTaskBuilderConstructor().constructTaskBuilder(Concurrency.SYNC, getTaskScheduler());
    }
    
    default B async()
    {
        return getTaskBuilderConstructor().constructTaskBuilder(Concurrency.ASYNC, getTaskScheduler());
    }
}
