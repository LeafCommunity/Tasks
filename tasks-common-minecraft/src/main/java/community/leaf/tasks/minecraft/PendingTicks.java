package community.leaf.tasks.minecraft;

import community.leaf.tasks.AbstractTaskBuilder;
import community.leaf.tasks.PendingMilliseconds;

import java.util.concurrent.TimeUnit;
import java.util.function.LongFunction;

public class PendingTicks<B extends AbstractTaskBuilder<B, ? extends PendingMilliseconds<B>>> extends PendingMilliseconds<B>
{
    public PendingTicks(LongFunction<B> pendingFunction, long units)
    {
        super(pendingFunction, units);
    }
    
    public B ticks()
    {
        return applyMilliseconds(Ticks.into(super.units, TimeUnit.MILLISECONDS));
    }
}
