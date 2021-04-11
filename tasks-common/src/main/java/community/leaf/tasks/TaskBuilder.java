package community.leaf.tasks;

import java.util.function.Function;

public class TaskBuilder extends AbstractTaskBuilder<TaskBuilder, PendingMilliseconds<TaskBuilder>>
{
    public TaskBuilder(Concurrency concurrency, TaskScheduler<?> scheduler)
    {
        super(concurrency, scheduler);
    }
    
    @Override
    protected PendingMilliseconds<TaskBuilder> generatePendingMilliseconds(
        Function<Long, TaskBuilder> function, long units
    )
    {
        return new PendingMilliseconds<>(function, units);
    }
}
