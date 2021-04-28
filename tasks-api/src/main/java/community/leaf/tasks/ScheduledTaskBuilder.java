package community.leaf.tasks;

public interface ScheduledTaskBuilder<T, B extends ScheduledTaskBuilder<T, B, P>, P extends PendingMilliseconds<B>>
    extends Schedule.Builder<B, P>, TaskRunner<T>, Unless.Builder
{
    interface Constructor<T, B extends ScheduledTaskBuilder<T, B, ? extends PendingMilliseconds<B>>>
    {
        B construct(TaskScheduler<T> scheduler, Concurrency concurrency);
    }
}
