/*
 * Copyright © 2021, RezzedUp <https://github.com/LeafCommunity/Tasks>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package community.leaf.tasks;

import java.util.concurrent.TimeUnit;
import java.util.function.LongFunction;

@SuppressWarnings("unused")
public class PendingMilliseconds<B extends AbstractTaskBuilder<B, ? extends PendingMilliseconds<B>>>
{
    protected final LongFunction<B> pendingFunction;
    protected final long units;
    
    public PendingMilliseconds(LongFunction<B> pendingFunction, long units)
    {
        this.pendingFunction = pendingFunction;
        this.units = units;
    }
    
    protected B applyMilliseconds(long milliseconds)
    {
        return pendingFunction.apply(milliseconds);
    }
    
    protected B applyUnit(TimeUnit unit)
    {
        return applyMilliseconds(TimeUnit.MILLISECONDS.convert(units, unit));
    }
    
    public B milliseconds() { return applyMilliseconds(units); }
    
    public B seconds() { return applyUnit(TimeUnit.SECONDS); }
    
    public B minutes() { return applyUnit(TimeUnit.MINUTES); }
    
    public B hours() { return applyUnit(TimeUnit.HOURS); }
    
    public B days() { return applyUnit(TimeUnit.DAYS); }
}
