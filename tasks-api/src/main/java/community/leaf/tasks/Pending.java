/*
 * Copyright © 2021, RezzedUp <https://github.com/LeafCommunity/Tasks>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package community.leaf.tasks;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.LongFunction;

@SuppressWarnings("unused")
public interface Pending<B extends Schedule.Builder<B, ? extends Pending<B>>>
{
    B milliseconds();
    
    B seconds();
    
    B minutes();
    
    B hours();
    
    B days();
    
    interface Constructor<B extends Schedule.Builder<B, P>, P extends Pending<B>>
    {
        P construct(LongFunction<B> function, long units);
    }
    
    class Milliseconds<B extends Schedule.Builder<B, ? extends Pending<B>>> implements Pending<B>
    {
        protected final LongFunction<B> function;
        protected final long units;
        
        public Milliseconds(LongFunction<B> function, long units)
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
}
