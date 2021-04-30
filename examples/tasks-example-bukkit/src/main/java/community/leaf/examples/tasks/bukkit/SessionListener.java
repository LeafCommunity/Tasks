package community.leaf.examples.tasks.bukkit;

import community.leaf.tasks.minecraft.PlayerSession;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class SessionListener implements Listener
{
    private final TasksExampleBukkitPlugin plugin;
    
    public SessionListener(TasksExampleBukkitPlugin plugin)
    {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        PlayerSession session = plugin.sessions().start(player);
        
        plugin.sync()
            .every(15).seconds().forever()
            .unless(session::isExpired)
            .run(task ->
            {
                Object[] colors = Arrays.stream(ChatColor.values()).filter(ChatColor::isColor).toArray();
                String random = String.valueOf(colors[ThreadLocalRandom.current().nextInt(colors.length)]);
                
                player.sendMessage(random + "Hello. " + ChatColor.ITALIC + "#" + (task.iterations() + 1));
            });
    }
    
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event)
    {
        plugin.sessions().end(event.getPlayer());
    }
}
