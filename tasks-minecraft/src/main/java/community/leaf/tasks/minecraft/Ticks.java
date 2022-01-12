/*
 * Copyright © 2021-2022, RezzedUp <https://github.com/LeafCommunity/Tasks>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package community.leaf.tasks.minecraft;

import community.leaf.tasks.Pending;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;
import java.util.function.LongFunction;

public interface Ticks<B> extends Pending<B>
{
    static <B> Ticks<B> pending(LongFunction<B> function, long units)
    {
        return new AbstractTicks<>(function, units) {};
    }
    
    B ticks();
    
    /**
     * Convert a duration of time into Minecraft ticks.
     *
     * @param duration  duration of time units
     * @param unit      unit of time
     *
     * @return ticks equivalent of the duration
     */
    static long of(long duration, TimeUnit unit)
    {
        // 1 tick = 50 milliseconds
        return TimeUnit.MILLISECONDS.convert(duration, unit) / 50;
    }
    
    /**
     * Convert a duration of time into Minecraft ticks.
     *
     * @param duration  duration of time units
     *
     * @return ticks equivalent of the duration
     */
    static long of(Duration duration)
    {
        return duration.toMillis() / 50;
    }
    
    /**
     * Converts Minecraft ticks into units of the provided unit.
     *
     * @param ticks     number of ticks
     * @param unit      unit of time
     *
     * @return provided-unit units of time equivalent to ticks
     */
    static long convertToUnit(long ticks, TimeUnit unit)
    {
        return unit.convert(ticks * 50, TimeUnit.MILLISECONDS);
    }
    
    /**
     * Converts from Minecraft ticks into a duration of time.
     *
     * @param ticks     number of ticks
     *
     * @return duration equivalent of provided ticks
     */
    static Duration duration(long ticks)
    {
        return Duration.of(ticks * 50, ChronoUnit.MILLIS);
    }
}
