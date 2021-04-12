package community.leaf.examples.tasks.bukkit;

import community.leaf.tasks.Tasks;
import community.leaf.tasks.bukkit.BukkitTaskSource;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class TasksExampleBukkitPlugin extends JavaPlugin implements BukkitTaskSource
{
    @Override
    public void onEnable()
    {
        getServer().getPluginManager().registerEvents(new SessionListener(this), this);
        
        sync().run(() ->
            getLogger().info(ChatColor.LIGHT_PURPLE + "Tasks Example Bukkit Plugin: success!")
        );
    
        Tasks.sync(this).delay(10).ticks().run(() -> getLogger().info("10 ticks later..."));
        
        sync().delay(1).seconds().repeat(5).every(3).seconds()
            .runWithContext(task -> {
                if (task.isFirstIteration()) { getLogger().info(ChatColor.GREEN + "FIRST ITERATION!"); }
                getLogger().info("Task iterations so far: " + ChatColor.GREEN + task.getIterations());
                if (task.isLastIteration()) { getLogger().info(ChatColor.GREEN + "LAST ITERATION."); }
            });
    }
    
    @Override
    public Plugin plugin() { return this; }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        sync().repeat(5).every(20).ticks()
            .runWithContext(task -> {
                sender.sendMessage("Test:" + ChatColor.LIGHT_PURPLE + " #" + (task.getIterations() + 1));
            });
        
        return true;
    }
}
