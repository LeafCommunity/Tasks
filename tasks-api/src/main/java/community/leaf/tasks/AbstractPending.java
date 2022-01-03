/*
 * Copyright © 2021-2022, RezzedUp <https://github.com/LeafCommunity/Tasks>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package community.leaf.tasks;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.LongFunction;

@SuppressWarnings("NullableProblems") // there are no such problems
public abstract class AbstractPending<B> implements Pending<B>
{
    protected final LongFunction<B> function;
    protected final long units;
    
    protected AbstractPending(LongFunction<B> function, long units)
    {
        this.function = Objects.requireNonNull(function, "function");
        this.units = units;
    }
    
    protected B applyMilliseconds(long milliseconds)
    {
        return function.apply(milliseconds);
    }
    
    protected B applyUnit(TimeUnit unit)
    {
        return applyMilliseconds(TimeUnit.MILLISECONDS.convert(units, unit));
    }
    
    @Override
    public B milliseconds() { return applyMilliseconds(units); }
    
    @Override
    public B seconds() { return applyUnit(TimeUnit.SECONDS); }
    
    @Override
    public B minutes() { return applyUnit(TimeUnit.MINUTES); }
    
    @Override
    public B hours() { return applyUnit(TimeUnit.HOURS); }
    
    @Override
    public B days() { return applyUnit(TimeUnit.DAYS); }
}
