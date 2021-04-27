/*
 * Copyright © 2021, RezzedUp <https://github.com/LeafCommunity/Tasks>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package community.leaf.tasks.minecraft;

import community.leaf.tasks.AbstractTaskBuilder;
import community.leaf.tasks.PendingMilliseconds;

import java.util.concurrent.TimeUnit;
import java.util.function.LongFunction;

public class PendingTicks<B extends AbstractTaskBuilder<B, ? extends PendingMilliseconds<B>>> extends PendingMilliseconds<B>
{
    public PendingTicks(LongFunction<B> pendingFunction, long units)
    {
        super(pendingFunction, units);
    }
    
    public B ticks()
    {
        return applyMilliseconds(Ticks.into(super.units, TimeUnit.MILLISECONDS));
    }
}
