/*
 * Copyright © 2021, RezzedUp <https://github.com/LeafCommunity/Tasks>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package community.leaf.tasks;

import pl.tlinkowski.annotation.basic.NullOr;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.LongFunction;

@SuppressWarnings({"UnusedReturnValue", "unused"})
public abstract class AbstractTaskBuilder<B extends AbstractTaskBuilder<B, P>, P extends PendingMilliseconds<B>> implements Schedulable
{
    protected final Concurrency concurrency;
    protected final TaskScheduler<?> scheduler;
    
    private long delay;
    private long period;
    
    private @NullOr List<Unless> cancellation = null;
    private Repeats.Expected repeats = Repeats.Constant.NEVER;
    
    public AbstractTaskBuilder(Concurrency concurrency, TaskScheduler<?> scheduler)
    {
        this.concurrency = Objects.requireNonNull(concurrency, "concurrency");
        this.scheduler = Objects.requireNonNull(scheduler, "scheduler");
    }
    
    protected abstract P pending(LongFunction<B> function, long units);
    
    @SuppressWarnings("unchecked")
    protected final B self() { return (B) this; }
    
    @Override
    public final Concurrency concurrency() { return concurrency; }
    
    @Override
    public long delay() { return delay; }
    
    public B delayByMilliseconds(long milliseconds)
    {
        delay = milliseconds;
        return self();
    }
    
    public B delay(long duration, TimeUnit unit)
    {
        return delayByMilliseconds(TimeUnit.MILLISECONDS.convert(duration, unit));
    }
    
    public P delay(long pendingDuration)
    {
        return pending(this::delayByMilliseconds, pendingDuration);
    }
    
    @Override
    public long period() { return period; }
    
    public B everyFewMilliseconds(long milliseconds)
    {
        if (repeats.until() == Repeats.NEVER) { repeats = Repeats.Constant.FOREVER; }
        period = milliseconds;
        return self();
    }
    
    public B every(long duration, TimeUnit unit)
    {
        return everyFewMilliseconds(TimeUnit.MILLISECONDS.convert(duration, unit));
    }
    
    public P every(long pendingDuration)
    {
        return pending(this::everyFewMilliseconds, pendingDuration);
    }
    
    @Override
    public Repeats.Expected repeats() { return repeats; }
    
    public B repeat(long repetitions)
    {
        repeats = Repeats.expect(repetitions);
        return self();
    }
    
    public B forever()
    {
        repeats = Repeats.Constant.FOREVER;
        return self();
    }
    
    public B unless(Unless criteria)
    {
        Objects.requireNonNull(criteria, "criteria");
        if (cancellation == null) { cancellation = new ArrayList<>(); }
        cancellation.add(criteria);
        return self();
    }
    
    protected boolean isAutoCancellable()
    {
        return (repeats.until() == Repeats.FINITE) || (cancellation != null && !cancellation.isEmpty());
    }
    
    protected List<Unless> copyCancellationCriteria()
    {
        if (cancellation == null || cancellation.isEmpty()) { return Collections.emptyList(); }
        return new ArrayList<>(cancellation);
    }
    
    public <R extends Runnable> R run(R runnable)
    {
        Objects.requireNonNull(runnable, "runnable");
        
        if (isAutoCancellable())
        {
            List<Unless> runs = copyCancellationCriteria();
            
            scheduler.schedule(this, task -> {
                if (task.isDoneRepeating() || runs.stream().anyMatch(Unless::criteria)) { task.cancel(); }
                else { runnable.run(); }
            });
        }
        else
        {
            scheduler.schedule(this, runnable);
        }
        return runnable;
    }
    
    public <R extends ContextualRunnable> R runWithContext(R runnable)
    {
        Objects.requireNonNull(runnable, "runnable");
        
        if (isAutoCancellable())
        {
            List<Unless> runs = copyCancellationCriteria();
            
            scheduler.schedule(this, task -> {
                if (task.isDoneRepeating() || runs.stream().anyMatch(Unless::criteria)) { task.cancel(); }
                else { runnable.run(task); }
            });
        }
        else
        {
            scheduler.schedule(this, runnable);
        }
        return runnable;
    }
}
