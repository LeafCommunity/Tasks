package community.leaf.tasks;

import pl.tlinkowski.annotation.basic.NullOr;

import java.util.Objects;

public class ScheduleImpl implements Schedule
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
        ScheduleImpl schedule = (ScheduleImpl) o;
        
        return delay == schedule.delay
            && period == schedule.period
            && concurrency == schedule.concurrency
            && repeats.equals(schedule.repeats);
    }
    
    @Override
    public int hashCode()
    {
        return Objects.hash(concurrency, delay, period, repeats);
    }
}
