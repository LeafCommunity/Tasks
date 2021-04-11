package community.leaf.tasks;

public class Tasks
{
    private Tasks() { throw new UnsupportedOperationException(); }
    
     public static <B extends AbstractTaskBuilder<B, ? extends PendingMilliseconds<B>>> B
        create(TaskBuilderConstructor<B> constructor, Concurrency concurrency, TaskScheduler<?> scheduler)
    {
        return constructor.constructTaskBuilder(concurrency, scheduler);
    }
    
    public static <B extends AbstractTaskBuilder<B, ? extends PendingMilliseconds<B>>> B
        sync(TaskBuilderConstructor<B> constructor, TaskScheduler<?> scheduler)
    {
        return create(constructor, Concurrency.SYNC, scheduler);
    }
    
    public static <B extends AbstractTaskBuilder<B, ? extends PendingMilliseconds<B>>> B sync(TaskBuilderSource<B> source)
    {
        return sync(source.getTaskBuilderConstructor(), source.getTaskScheduler());
    }
    
    public static TaskBuilder sync(TaskScheduler<?> scheduler)
    {
        return sync(TaskBuilder::new, scheduler);
    }
    
    public static <B extends AbstractTaskBuilder<B, ? extends PendingMilliseconds<B>>> B
        async(TaskBuilderConstructor<B> constructor, TaskScheduler<?> scheduler)
    {
        return create(constructor, Concurrency.ASYNC, scheduler);
    }
    
    public static <B extends AbstractTaskBuilder<B, ? extends PendingMilliseconds<B>>> B async(TaskBuilderSource<B> source)
    {
        return async(source.getTaskBuilderConstructor(), source.getTaskScheduler());
    }
    
    public static TaskBuilder async(TaskScheduler<?> scheduler)
    {
        return async(TaskBuilder::new, scheduler);
    }
}
