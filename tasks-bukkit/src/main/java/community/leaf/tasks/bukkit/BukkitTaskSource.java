/*
 * Copyright © 2021, RezzedUp <https://github.com/LeafCommunity/Tasks>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package community.leaf.tasks.bukkit;

import community.leaf.tasks.ConcurrentTaskSource;
import community.leaf.tasks.TaskBuilderConstructor;
import community.leaf.tasks.minecraft.MinecraftTaskBuilder;

@FunctionalInterface
public interface BukkitTaskSource extends ConcurrentTaskSource<MinecraftTaskBuilder>, PlayerSessionSource, PluginSource
{
    @Override
    default BukkitTaskScheduler getTaskScheduler()
    {
        return this::plugin;
    }
    
    @Override
    default TaskBuilderConstructor<MinecraftTaskBuilder> getTaskBuilderConstructor()
    {
        return MinecraftTaskBuilder::new;
    }
}
