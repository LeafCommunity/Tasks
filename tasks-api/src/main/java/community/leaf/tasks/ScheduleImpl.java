/*
 * Copyright © 2021-2022, RezzedUp <https://github.com/LeafCommunity/Tasks>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package community.leaf.tasks;

import pl.tlinkowski.annotation.basic.NullOr;

import java.util.Objects;

class ScheduleImpl implements Schedule
{
    private final Concurrency concurrency;
    private final long delay;
    private final long period;
    private final Repeats.Expected repeats;
    
    ScheduleImpl(Concurrency concurrency, long delay, long period, Repeats.Expected repeats)
    {
        this.concurrency = Objects.requireNonNull(concurrency, "concurrency");
        this.delay = delay;
        this.period = period;
        this.repeats = Objects.requireNonNull(repeats, "repeats");
    }
    
    @Override
    public Concurrency concurrency() { return concurrency; }
    
    @Override
    public long delay() { return delay; }
    
    @Override
    public long period() { return period; }
    
    @Override
    public Repeats.Expected repeats() { return repeats; }
    
    @Override
    public String toString()
    {
        return "ScheduleImpl{" +
            "concurrency=" + concurrency +
            ", delay=" + delay +
            ", period=" + period +
            ", repeats=" + repeats +
            '}';
    }
    
    @Override
    public boolean equals(@NullOr Object o)
    {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        
        ScheduleImpl that = (ScheduleImpl) o;
        return delay == that.delay
            && period == that.period
            && concurrency == that.concurrency
            && repeats.equals(that.repeats);
    }
    
    @Override
    public int hashCode()
    {
        return Objects.hash(concurrency, delay, period, repeats);
    }
}
