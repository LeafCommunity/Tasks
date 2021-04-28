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
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

public abstract class WrappedTaskContext<T> implements TaskContext
{
    private final AtomicLong iterations = new AtomicLong();
    private final AtomicReference<@NullOr T> wrapped = new AtomicReference<>();
    
    private final Repeats.Expected repeats;
    
    public WrappedTaskContext(Repeats.Expected repeats)
    {
        this.repeats = Objects.requireNonNull(repeats, "repeats");
    }
    
    @Override
    public final long iterations() { return iterations.get(); }
    
    public final void iterate() { iterations.incrementAndGet(); }
    
    @Override
    public final Repeats.Expected repeats() { return repeats; }
    
    public final T task()
    {
        @NullOr T task = wrapped.get();
        if (task != null) { return task; }
        throw new IllegalStateException("No task wrapped yet");
    }
    
    public final T wrap(T unwrapped)
    {
        // If compareAndExchange() is successful, it will return null since it
        // always returns the current value (which should not by initialized yet).
        // Otherwise, throw an exception for attempting to re-wrap with something else.
        
        if (wrapped.compareAndExchange(null, unwrapped) == null) { return unwrapped; }
        throw new IllegalStateException("Already contains a wrapped task");
    }
}
