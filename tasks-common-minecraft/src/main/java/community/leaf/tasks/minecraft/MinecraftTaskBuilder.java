package community.leaf.tasks.minecraft;

import community.leaf.tasks.Concurrency;
import community.leaf.tasks.AbstractTaskBuilder;
import community.leaf.tasks.TaskScheduler;

import java.util.function.LongFunction;

public class MinecraftTaskBuilder extends AbstractTaskBuilder<MinecraftTaskBuilder, PendingTicks<MinecraftTaskBuilder>>
{
    public MinecraftTaskBuilder(Concurrency concurrency, TaskScheduler<?> scheduler)
    {
        super(concurrency, scheduler);
    }
    
    @Override
    protected PendingTicks<MinecraftTaskBuilder> pending(LongFunction<MinecraftTaskBuilder> function, long units)
    {
        return new PendingTicks<>(function, units);
    }
}
