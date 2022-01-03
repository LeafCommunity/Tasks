/*
 * Copyright © 2021-2022, RezzedUp <https://github.com/LeafCommunity/Tasks>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package community.leaf.tasks.bukkit;

import community.leaf.tasks.Concurrency;
import community.leaf.tasks.Schedule;
import community.leaf.tasks.TaskContext;
import community.leaf.tasks.TaskScheduler;
import community.leaf.tasks.minecraft.Ticks;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import java.util.concurrent.TimeUnit;

@FunctionalInterface
public interface BukkitTaskScheduler extends PluginSource, TaskScheduler<BukkitTask>
{
    @Override
    default TaskContext<BukkitTask> context(Schedule schedule)
    {
        return new BukkitTaskContext(schedule);
    }
    
    @Override
    default BukkitTask now(Concurrency concurrency, Runnable runnable)
    {
        Plugin plugin = plugin();
        BukkitScheduler scheduler = plugin.getServer().getScheduler();
    
        if (concurrency == Concurrency.SYNC)
        {
            return scheduler.runTask(plugin, runnable);
        }
        else
        {
            return scheduler.runTaskAsynchronously(plugin, runnable);
        }
    }
    
    @Override
    default BukkitTask future(Concurrency concurrency, Runnable runnable, long delay)
    {
        Plugin plugin = plugin();
        BukkitScheduler scheduler = plugin.getServer().getScheduler();
        
        long delayInTicks = Ticks.from(delay, TimeUnit.MILLISECONDS);
        
        if (concurrency == Concurrency.SYNC)
        {
            return scheduler.runTaskLater(plugin, runnable, delayInTicks);
        }
        else
        {
            return scheduler.runTaskLaterAsynchronously(plugin, runnable, delayInTicks);
        }
    }
    
    @Override
    default BukkitTask repeat(Concurrency concurrency, Runnable runnable, long delay, long period)
    {
        Plugin plugin = plugin();
        BukkitScheduler scheduler = plugin.getServer().getScheduler();
        
        long delayInTicks = Ticks.from(delay, TimeUnit.MILLISECONDS);
        long periodInTicks = Ticks.from(period, TimeUnit.MILLISECONDS);
    
        if (concurrency == Concurrency.SYNC)
        {
            return scheduler.runTaskTimer(plugin, runnable, delayInTicks, periodInTicks);
        }
        else
        {
            return scheduler.runTaskTimerAsynchronously(plugin, runnable, delayInTicks, periodInTicks);
        }
    }
}
