package community.leaf.tasks;

public interface TaskBuilderSource<B extends AbstractTaskBuilder<B, ? extends PendingMilliseconds<B>>>
{
    TaskScheduler<?> getTaskScheduler();
    
    TaskBuilderConstructor<B> getTaskBuilderConstructor();
}
