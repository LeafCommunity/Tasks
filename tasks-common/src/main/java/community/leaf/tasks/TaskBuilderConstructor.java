package community.leaf.tasks;

public interface TaskBuilderConstructor<B extends AbstractTaskBuilder<B, ? extends PendingMilliseconds<B>>>
{
    B construct(Concurrency concurrency, TaskScheduler<?> scheduler);
}
