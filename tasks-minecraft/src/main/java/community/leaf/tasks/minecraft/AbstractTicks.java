/*
 * Copyright © 2021-2024, RezzedUp <https://github.com/LeafCommunity/Tasks>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package community.leaf.tasks.minecraft;

import community.leaf.tasks.AbstractPending;

import java.util.concurrent.TimeUnit;
import java.util.function.LongFunction;

public abstract class AbstractTicks<B> extends AbstractPending<B> implements Ticks<B>
{
    protected AbstractTicks(LongFunction<B> pendingFunction, long units)
    {
        super(pendingFunction, units);
    }
    
    @Override
    public B ticks()
    {
        return applyMilliseconds(Ticks.convertToUnit(super.units, TimeUnit.MILLISECONDS));
    }
}
