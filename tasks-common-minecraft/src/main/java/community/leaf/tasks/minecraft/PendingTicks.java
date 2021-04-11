package community.leaf.tasks.minecraft;

import community.leaf.tasks.AbstractTaskBuilder;
import community.leaf.tasks.PendingMilliseconds;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class PendingTicks<B extends AbstractTaskBuilder<B, ? extends PendingMilliseconds<B>>> extends PendingMilliseconds<B>
{
    public PendingTicks(Function<Long, B> pendingFunction, long units)
    {
        super(pendingFunction, units);
    }
    
    public B ticks()
    {
        return applyMilliseconds(Ticks.into(super.units, TimeUnit.MILLISECONDS));
    }
}
