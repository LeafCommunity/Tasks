/*
 * Copyright © 2021, RezzedUp <https://github.com/LeafCommunity/Tasks>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package community.leaf.tasks.minecraft;

import community.leaf.tasks.Concurrency;
import community.leaf.tasks.AbstractTaskBuilder;
import community.leaf.tasks.TaskScheduler;

import java.util.function.LongFunction;

public class MinecraftTaskBuilder extends AbstractTaskBuilder<MinecraftTaskBuilder, PendingTicks<MinecraftTaskBuilder>>
{
    public MinecraftTaskBuilder(Concurrency concurrency, TaskScheduler<?> scheduler)
    {
        super(concurrency, scheduler);
    }
    
    @Override
    protected PendingTicks<MinecraftTaskBuilder> pending(LongFunction<MinecraftTaskBuilder> function, long units)
    {
        return new PendingTicks<>(function, units);
    }
}
