package community.leaf.tasks;

public interface SyncTaskSource<B extends AbstractTaskBuilder<B, ? extends PendingMilliseconds<B>>>
    extends TaskBuilderSource<B>
{
    default B sync()
    {
        return getTaskBuilderConstructor().construct(Concurrency.SYNC, getTaskScheduler());
    }
}
