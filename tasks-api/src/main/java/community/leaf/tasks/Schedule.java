/*
 * Copyright © 2021, RezzedUp <https://github.com/LeafCommunity/Tasks>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package community.leaf.tasks;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.function.LongFunction;

public interface Schedule
{
    static Schedule schedule(Concurrency concurrency, long delay, long period, Repeats.Expected repeats)
    {
        return new ScheduleImpl(concurrency, delay, period, repeats);
    }
    
    static Schedule immutable(Schedule schedule)
    {
        if (schedule instanceof ScheduleImpl) { return schedule; }
        return schedule(schedule.concurrency(), schedule.delay(), schedule.period(), schedule.repeats());
    }
    
    Concurrency concurrency();
    
    long delay();
    
    long period();
    
    Repeats.Expected repeats();
    
    default boolean isDelayed() { return delay() > 0; }
    
    interface Source
    {
        Schedule schedule();
    }
    
    interface Builder<B extends Builder<B, P>, P extends Pending<B>> extends Source
    {
        P pending(LongFunction<B> function, long units);
        
        B delayByMilliseconds(long milliseconds);
        
        B everyFewMilliseconds(long milliseconds);
        
        B repeat(Repeats.Expected repeats);
        
        default B delay(long duration, TimeUnit unit)
        {
            return delayByMilliseconds(TimeUnit.MILLISECONDS.convert(duration, unit));
        }
        
        default B delay(Duration duration)
        {
            return delayByMilliseconds(duration.toMillis());
        }
        
        default P delay(long duration)
        {
            return pending(this::delayByMilliseconds, duration);
        }
        
        default B every(long duration, TimeUnit unit)
        {
            return everyFewMilliseconds(TimeUnit.MILLISECONDS.convert(duration, unit));
        }
        
        default B every(Duration duration)
        {
            return delayByMilliseconds(duration.toMillis());
        }
        
        default P every(long duration)
        {
            return pending(this::everyFewMilliseconds, duration);
        }
        
        default B repeat(long repetitions)
        {
            return repeat(Repeats.expect(repetitions));
        }
        
        default B forever()
        {
            return repeat(Repeats.Constantly.FOREVER);
        }
        
        default B once()
        {
            return repeat(Repeats.Constantly.NEVER);
        }
    }
}
