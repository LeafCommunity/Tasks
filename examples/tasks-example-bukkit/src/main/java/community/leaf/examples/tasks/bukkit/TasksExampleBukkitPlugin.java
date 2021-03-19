package community.leaf.examples.tasks.bukkit;

import community.leaf.tasks.Tasks;
import community.leaf.tasks.bukkit.BukkitTaskScheduler;
import community.leaf.tasks.bukkit.BukkitTaskSource;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.TimeUnit;

public class TasksExampleBukkitPlugin extends JavaPlugin implements BukkitTaskSource
{
    @Override
    public void onEnable()
    {
        sync().run(() ->
            getLogger().info(ChatColor.LIGHT_PURPLE + "Tasks Example Bukkit Plugin: success!")
        );
    
        Tasks.sync(this).delay(10).run(() -> getLogger().info("10 ticks later..."));
        
        sync().delay(1, TimeUnit.SECONDS)
            .repeat(5)
            .every(3, TimeUnit.SECONDS)
            .runWithContext(task -> {
                if (task.isFirstIteration()) { getLogger().info(ChatColor.GREEN + "FIRST ITERATION!"); }
                getLogger().info("Task iterations so far: " + ChatColor.GREEN + task.getIterations());
                if (task.isLastIteration()) { getLogger().info(ChatColor.GREEN + "LAST ITERATION."); }
            });
    }
    
    @Override
    public BukkitTaskScheduler getTaskScheduler() { return () -> this; }
}
