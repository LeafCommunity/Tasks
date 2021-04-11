package community.leaf.tasks;

public interface TaskBuilderConstructor<B extends AbstractTaskBuilder<B, ? extends PendingMilliseconds<B>>>
{
    B constructTaskBuilder(Concurrency concurrency, TaskScheduler<?> scheduler);
}
