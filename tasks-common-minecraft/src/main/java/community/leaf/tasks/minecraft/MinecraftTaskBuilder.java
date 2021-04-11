package community.leaf.tasks.minecraft;

import community.leaf.tasks.Concurrency;
import community.leaf.tasks.AbstractTaskBuilder;
import community.leaf.tasks.TaskScheduler;

import java.util.function.Function;

public class MinecraftTaskBuilder extends AbstractTaskBuilder<MinecraftTaskBuilder, PendingTicks<MinecraftTaskBuilder>>
{
    public MinecraftTaskBuilder(Concurrency concurrency, TaskScheduler<?> scheduler)
    {
        super(concurrency, scheduler);
    }
    
    @Override
    protected PendingTicks<MinecraftTaskBuilder> generatePendingMilliseconds(
        Function<Long, MinecraftTaskBuilder> function, long units
    )
    {
        return new PendingTicks<>(function, units);
    }
}
