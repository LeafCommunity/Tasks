package community.leaf.tasks.bukkit;

import community.leaf.tasks.Unless;
import org.bukkit.entity.Player;

import java.util.Optional;

@SuppressWarnings({"UnusedReturnValue", "unused"})
@FunctionalInterface
public interface PlayerSessionDelegate extends PluginProvider
{
    default PlayerSession start(Player player) { return PlayerSession.start(plugin(), player); }
    
    default Optional<PlayerSession> get(Player player) { return PlayerSession.get(plugin(), player); }
    
    default PlayerSession getOrStart(Player player) { return PlayerSession.getOrStart(plugin(), player); }
    
    default void end(Player player) { PlayerSession.end(plugin(), player); }
    
    default Unless expired(Player player) { return PlayerSession.expired(plugin(), player); }
}
