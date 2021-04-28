/*
 * Copyright © 2021, RezzedUp <https://github.com/LeafCommunity/Tasks>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package community.leaf.tasks.bukkit;

import community.leaf.tasks.ScheduledTaskBuilder;
import community.leaf.tasks.TaskSource;
import community.leaf.tasks.minecraft.MinecraftTaskBuilder;
import org.bukkit.scheduler.BukkitTask;

@FunctionalInterface
public interface BukkitTaskSource extends
    PlayerSessionSource,
    PluginSource,
    TaskSource.Concurrent<BukkitTask, MinecraftTaskBuilder<BukkitTask>>
{
    @Override
    default BukkitTaskScheduler getTaskScheduler()
    {
        return this::plugin;
    }
    
    @Override
    default ScheduledTaskBuilder.Constructor<BukkitTask, MinecraftTaskBuilder<BukkitTask>>
        getTaskBuilderConstructor()
    {
        return MinecraftTaskBuilder::new;
    }
}
