package community.leaf.tasks.bukkit;

import community.leaf.tasks.Concurrency;
import community.leaf.tasks.TaskScheduler;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

@FunctionalInterface
public interface BukkitTaskScheduler extends TaskScheduler<BukkitTask>
{
    Plugin getPlugin();
    
    @Override
    default BukkitTaskContext createTaskContext(long repetitions)
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
            return scheduler.runTaskLater(plugin, runnable, delay);
        }
        else
        {
            return scheduler.runTaskLaterAsynchronously(plugin, runnable, delay);
        }
    }
    
    @Override
    default BukkitTask runRecurringTask(Concurrency concurrency, Runnable runnable, long delay, long period)
    {
        Plugin plugin = getPlugin();
        BukkitScheduler scheduler = plugin.getServer().getScheduler();
    
        if (concurrency == Concurrency.SYNC)
        {
            return scheduler.runTaskTimer(plugin, runnable, delay, period);
        }
        else
        {
            return scheduler.runTaskTimerAsynchronously(plugin, runnable, delay, period);
        }
    }
}
