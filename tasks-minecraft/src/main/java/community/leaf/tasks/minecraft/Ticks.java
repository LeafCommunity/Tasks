/*
 * Copyright © 2021, RezzedUp <https://github.com/LeafCommunity/Tasks>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package community.leaf.tasks.minecraft;

import community.leaf.tasks.Pending;
import community.leaf.tasks.Schedule;

import java.util.concurrent.TimeUnit;
import java.util.function.LongFunction;

public interface Ticks<B extends Schedule.Builder<B, ? extends Pending<B>>> extends Pending<B>
{
    /**
     * Convert from a duration of time to Minecraft ticks.
     *
     * @param sourceDuration    duration of time units
     * @param sourceUnit        unit of time
     * @return  ticks equivalent of the duration
     */
    static long from(long sourceDuration, TimeUnit sourceUnit)
    {
        // 1 tick = 50 milliseconds
        return TimeUnit.MILLISECONDS.convert(sourceDuration, sourceUnit) / 50;
    }
    
    /**
     * Convert from a duration of time to Minecraft ticks.
     *
     * @param sourceDuration    duration of integer time units
     * @param sourceUnit        unit of time
     * @return  ticks equivalent of the duration as an integer
     */
    static int fromInteger(int sourceDuration, TimeUnit sourceUnit)
    {
        return (int) from(sourceDuration, sourceUnit);
    }
    
    /**
     * Convert from Minecraft ticks to a duration of time.
     *
     * @param ticks number of ticks
     * @param unit  unit of time
     * @return      units of time equivalent to ticks
     */
    static long into(long ticks, TimeUnit unit)
    {
        return unit.convert(ticks * 50, TimeUnit.MILLISECONDS);
    }
    
    /**
     * Convert from Minecraft ticks to a duration of time.
     *
     * @param ticks integer number of ticks
     * @param unit  unit of time
     * @return      units of time equivalent to ticks as an integer
     */
    static int intoInteger(int ticks, TimeUnit unit)
    {
        return Long.valueOf(into(ticks, unit)).intValue();
    }
    
    B ticks();
    
    class PendingTicks<B extends Schedule.Builder<B, ? extends Pending<B>>> extends Milliseconds<B> implements Ticks<B>
    {
        public PendingTicks(LongFunction<B> pendingFunction, long units)
        {
            super(pendingFunction, units);
        }
        
        @Override
        public B ticks()
        {
            return applyMilliseconds(into(super.units, TimeUnit.MILLISECONDS));
        }
    }
}
