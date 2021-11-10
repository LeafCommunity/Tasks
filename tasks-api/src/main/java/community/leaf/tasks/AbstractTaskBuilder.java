/*
 * Copyright © 2021, RezzedUp <https://github.com/LeafCommunity/Tasks>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package community.leaf.tasks;

import pl.tlinkowski.annotation.basic.NullOr;

import java.util.Objects;
import java.util.function.LongFunction;

@SuppressWarnings({"UnusedReturnValue", "unused"})
public abstract class AbstractTaskBuilder<T, B extends ScheduledTaskBuilder<T, B>, P extends Pending<B>>
    implements ScheduledTaskBuilder<T, B>
{
    protected final TaskScheduler<T> scheduler;
    protected final Concurrency concurrency;
    protected final Pending.Constructor<B, P> pending;
    
    private long delay;
    private long period;
    
    private Repeats.Expected repeats = Repeats.Constantly.NEVER;
    
    @SuppressWarnings("NullableProblems") // ?
    private Unless.@NullOr Builder<?> cancellation = null;
    
    protected AbstractTaskBuilder(TaskScheduler<T> scheduler, Concurrency concurrency, Pending.Constructor<B, P> pending)
    {
        this.scheduler = Objects.requireNonNull(scheduler, "scheduler");
        this.concurrency = Objects.requireNonNull(concurrency, "concurrency");
        this.pending = Objects.requireNonNull(pending, "pending");
    }
    
    @SuppressWarnings("unchecked")
    protected final B self() { return (B) this; }
    
    protected final P pending(LongFunction<B> function, long units) { return pending.construct(function, units); }
    
    @Override
    public final Schedule schedule() { return Schedule.schedule(concurrency, delay, period, repeats); }
    
    @Override
    public final B delayByMilliseconds(long milliseconds)
    {
        delay = milliseconds;
        return self();
    }
    
    @Override
    public P delay(long duration)
    {
        return pending(this::delayByMilliseconds, duration);
    }
    
    @Override
    public final B everyFewMilliseconds(long milliseconds)
    {
        if (repeats.until() == Repeats.NEVER) { repeats = Repeats.Constantly.FOREVER; }
        period = milliseconds;
        return self();
    }
    
    @Override
    public P every(long duration)
    {
        return pending(this::everyFewMilliseconds, duration);
    }
    
    @Override
    public final B repeat(Repeats.Expected repeats)
    {
        this.repeats = Objects.requireNonNull(repeats, "repeats");
        return self();
    }
    
    @Override
    public final B unless(Unless criteria)
    {
        Objects.requireNonNull(criteria, "criteria");
        if (cancellation == null) { cancellation = Unless.builder(); }
        cancellation.unless(criteria);
        return self();
    }
    
    @Override
    public final Unless unless()
    {
        return (cancellation == null) ? Unless::never : cancellation.unless();
    }
    
    protected final boolean isAutoCancellable()
    {
        return repeats.until() == Repeats.FINITE || cancellation != null;
    }
    
    @Override
    public final TaskContext<T> run(ContextualRunnable<T> runnable)
    {
        Objects.requireNonNull(runnable, "runnable");
        
        if (isAutoCancellable())
        {
            Unless cancel = unless();
            
            return scheduler.schedule(schedule(), task -> {
                if (task.isDoneRepeating() || cancel.criteria()) { task.cancel(); }
                else { runnable.run(task); }
            });
        }
        else
        {
            return scheduler.schedule(schedule(), runnable);
        }
    }
}
