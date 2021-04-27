package community.leaf.tasks;

public interface AsyncTaskSource<B extends AbstractTaskBuilder<B, ? extends PendingMilliseconds<B>>>
    extends TaskBuilderSource<B>
{
    default B async()
    {
        return getTaskBuilderConstructor().construct(Concurrency.ASYNC, getTaskScheduler());
    }
}
