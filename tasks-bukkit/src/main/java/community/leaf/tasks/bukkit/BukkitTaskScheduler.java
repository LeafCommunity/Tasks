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
public interface BukkitTaskScheduler extends PluginProvider, TaskScheduler<BukkitTask>
{
    @Override
    default BukkitTaskContext createTaskContext(Repeats.Expected repetitions)
    {
        return new BukkitTaskContext(repetitions);
    }
    
    @Override
    default BukkitTask runTask(Concurrency concurrency, Runnable runnable)
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
    default BukkitTask runFutureTask(Concurrency concurrency, Runnable runnable, long delay)
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
    default BukkitTask runRepeatingTask(Concurrency concurrency, Runnable runnable, long delay, long period)
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
