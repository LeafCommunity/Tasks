package community.leaf.tasks.bukkit;

import community.leaf.tasks.Unless;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import pl.tlinkowski.annotation.basic.NullOr;

import java.lang.ref.WeakReference;
import java.util.Objects;
import java.util.Optional;

public class PlayerSession
{
    public static final String KEY = PlayerSession.class.getName();
    
    private static void validate(Plugin plugin, Player player)
    {
        Objects.requireNonNull(plugin, "plugin");
        Objects.requireNonNull(player, "player");
    }
    
    public static PlayerSession start(Plugin plugin, Player player)
    {
        validate(plugin, player);
        
        PlayerSession session = new PlayerSession(plugin, player);
        player.setMetadata(KEY, new FixedMetadataValue(plugin, session));
        return session;
    }
    
    public static Optional<PlayerSession> get(Plugin plugin, Player player)
    {
        validate(plugin, player);
        
        for (MetadataValue meta : player.getMetadata(KEY))
        {
            if (!plugin.equals(meta.getOwningPlugin())) { continue; }
            
            @NullOr Object value = meta.value();
            if (!(value instanceof PlayerSession)) { continue; }
            
            return Optional.of((PlayerSession) value);
        }
        
        return Optional.empty();
    }
    
    public static PlayerSession getOrStart(Plugin plugin, Player player)
    {
        // validated in get() and start()
        return get(plugin, player).orElseGet(() -> start(plugin, player));
    }
    
    public static void end(Plugin plugin, Player player)
    {
        validate(plugin, player);
        player.removeMetadata(KEY, plugin);
    }
    
    public static Unless expired(Plugin plugin, Player player)
    {
        // validated in getOrStart()
        PlayerSession session = getOrStart(plugin, player);
        return session::isExpired;
    }
    
    private static boolean isPluginValid(@NullOr Plugin plugin)
    {
        return plugin != null && plugin.isEnabled();
    }
    
    private static boolean isPlayerOnline(@NullOr Player player)
    {
        return player != null && player.isOnline();
    }
    
    private final WeakReference<Plugin> pluginReference;
    private final WeakReference<Player> playerReference;
    
    private PlayerSession(Plugin plugin, Player player)
    {
        this.pluginReference = new WeakReference<>(plugin);
        this.playerReference = new WeakReference<>(player);
    }
    
    public @NullOr Plugin pluginOrNull() { return pluginReference.get(); }
    
    public @NullOr Player playerOrNull() { return playerReference.get(); }
    
    public void end()
    {
        @NullOr Plugin plugin = pluginOrNull();
        @NullOr Player player = playerOrNull();
        
        if (player == null || !isPluginValid(plugin)) { return; }
        if (PlayerSession.get(plugin, player).filter(this::equals).isEmpty()) { return; }
        
        player.removeMetadata(KEY, plugin);
    }
    
    public boolean isActive()
    {
        @NullOr Plugin plugin = pluginOrNull();
        @NullOr Player player = playerOrNull();
        
        return isPlayerOnline(player) && isPluginValid(plugin) &&
            PlayerSession.get(plugin, player).filter(this::equals).isPresent();
    }
    
    public boolean isExpired() { return !isActive(); }
    
    @Override
    public String toString()
    {
        return "PlayerSession{" +
            "pluginReference=" + pluginReference +
            ", playerReference=" + playerReference +
            "}@" + Integer.toHexString(hashCode());
    }
}
