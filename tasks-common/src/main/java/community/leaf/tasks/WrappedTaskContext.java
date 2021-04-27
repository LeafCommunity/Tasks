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

public abstract class WrappedTaskContext<T> implements TaskContext
{
    private long iterations = 0;
    private @NullOr T wrapped = null;
    
    private final Repeats.Expected repeats;
    
    public WrappedTaskContext(Repeats.Expected repeats)
    {
        this.repeats = Objects.requireNonNull(repeats, "repeats");
    }
    
    @Override
    public final long iterations() { return this.iterations; }
    
    public final void iterate() { this.iterations += 1; }
    
    @Override
    public final Repeats.Expected repeats() { return repeats; }
    
    public final T task()
    {
        if (this.wrapped != null) { return this.wrapped; }
        throw new IllegalStateException("No task wrapped yet.");
    }
    
    public final T wrap(T unwrapped)
    {
        if (this.wrapped == null) { return this.wrapped = Objects.requireNonNull(unwrapped, "unwrapped"); }
        throw new IllegalStateException("Already contains a wrapped task.");
    }
}
