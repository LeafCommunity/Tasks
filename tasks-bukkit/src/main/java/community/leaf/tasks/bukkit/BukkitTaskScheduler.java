package community.leaf.tasks.bukkit;

import community.leaf.tasks.Concurrency;
import community.leaf.tasks.Repeats;
import community.leaf.tasks.TaskScheduler;
import community.leaf.tasks.minecraft.Ticks;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import java.util.concurrent.TimeUnit;

@FunctionalInterface
public interface BukkitTaskScheduler extends TaskScheduler<BukkitTask>
{
    Plugin getPlugin();
    
    @Override
    default BukkitTaskContext createTaskContext(Repeats.Expected repetitions)
    {
        return new BukkitTaskContext(repetitions);
    }
    
    @Override
    default BukkitTask runTask(Concurrency concurrency, Runnable runnable)
    {
        Plugin plugin = getPlugin();
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
    default BukkitTask runFutureTask(Concurrency concurrency, Runnable runnable, long delay)
    {
        Plugin plugin = getPlugin();
        BukkitScheduler scheduler = plugin.getServer().getScheduler();
    
        if (concurrency == Concurrency.SYNC)
        {
            return scheduler.runTaskLater(
                plugin, runnable, Ticks.from(delay, TimeUnit.MILLISECONDS)
            );
        }
        else
        {
            return scheduler.runTaskLaterAsynchronously(
                plugin, runnable, Ticks.from(delay, TimeUnit.MILLISECONDS)
            );
        }
    }
    
    @Override
    default BukkitTask runRepeatingTask(Concurrency concurrency, Runnable runnable, long delay, long period)
    {
        Plugin plugin = getPlugin();
        BukkitScheduler scheduler = plugin.getServer().getScheduler();
    
        if (concurrency == Concurrency.SYNC)
        {
            return scheduler.runTaskTimer(
                plugin, runnable, Ticks.from(delay, TimeUnit.MILLISECONDS), Ticks.from(delay, TimeUnit.MILLISECONDS)
            );
        }
        else
        {
            return scheduler.runTaskTimerAsynchronously(
                plugin, runnable, Ticks.from(delay, TimeUnit.MILLISECONDS), Ticks.from(delay, TimeUnit.MILLISECONDS)
            );
        }
    }
}
