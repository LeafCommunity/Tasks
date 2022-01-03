/*
 * Copyright © 2021-2022, RezzedUp <https://github.com/LeafCommunity/Tasks>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package community.leaf.tasks.minecraft;

import community.leaf.tasks.AbstractTaskBuilder;
import community.leaf.tasks.Concurrency;
import community.leaf.tasks.TaskScheduler;

final class MinecraftTaskBuilderImpl<T>
    extends AbstractTaskBuilder<T, MinecraftTaskBuilder<T>, Ticks<MinecraftTaskBuilder<T>>>
    implements MinecraftTaskBuilder<T>
{
    MinecraftTaskBuilderImpl(TaskScheduler<T> scheduler, Concurrency concurrency)
    {
        super(scheduler, concurrency, Ticks::pending);
    }
}
