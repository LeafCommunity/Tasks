package community.leaf.examples.tasks.bukkit;

import community.leaf.tasks.Tasks;
import community.leaf.tasks.bukkit.BukkitTaskScheduler;
import community.leaf.tasks.bukkit.BukkitTaskSource;
import community.leaf.tasks.bukkit.PlayerSession;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class TasksExampleBukkitPlugin extends JavaPlugin implements BukkitTaskSource, Listener
{
    @Override
    public void onEnable()
    {
        getServer().getPluginManager().registerEvents(this, this);
        
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
    public BukkitTaskScheduler getTaskScheduler() { return () -> this; }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        sync().repeat(5).every(20).ticks()
            .runWithContext(task -> {
                sender.sendMessage("Test:" + ChatColor.LIGHT_PURPLE + " #" + (task.getIterations() + 1));
            });
        
        return true;
    }
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        PlayerSession.start(this, player);
        
        sync().every(15).seconds().forever()
            .unless(PlayerSession.expires(this, player))
            .runWithContext(task ->
            {
                Object[] colors = Arrays.stream(ChatColor.values()).filter(ChatColor::isColor).toArray();
                String random = String.valueOf(colors[ThreadLocalRandom.current().nextInt(colors.length)]);
                
                player.sendMessage(random + "Hello. " + ChatColor.ITALIC + "#" + (task.getIterations() + 1));
            });
    }
    
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event)
    {
        PlayerSession.end(this, event.getPlayer());
    }
    
}
