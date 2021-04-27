package community.leaf.tasks;

public interface ConcurrentTaskSource<B extends AbstractTaskBuilder<B, ? extends PendingMilliseconds<B>>>
    extends SyncTaskSource<B>, AsyncTaskSource<B> {}
