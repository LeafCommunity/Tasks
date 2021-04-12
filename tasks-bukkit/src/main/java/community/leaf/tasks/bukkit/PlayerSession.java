package community.leaf.tasks.bukkit;

import community.leaf.tasks.Unless;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import pl.tlinkowski.annotation.basic.NullOr;

import java.lang.ref.WeakReference;
import java.util.Optional;

public class PlayerSession
{
    public static final String KEY = PlayerSession.class.getName();
    
    public static PlayerSession start(Plugin plugin, Player player)
    {
        PlayerSession session = new PlayerSession(plugin, player);
        player.setMetadata(KEY, new FixedMetadataValue(plugin, session));
        return session;
    }
    
    public static Optional<PlayerSession> get(Plugin plugin, Player player)
    {
        for (MetadataValue meta : player.getMetadata(KEY))
        {
            if (!plugin.equals(meta.getOwningPlugin())) { continue; }
            
            @NullOr Object value = meta.value();
            if (!(value instanceof PlayerSession)) { continue; }
            
            return Optional.of((PlayerSession) value);
        }
        
        return Optional.empty();
    }
    
    public static Unless expires(Plugin plugin, Player player)
    {
        PlayerSession session = getOrStart(plugin, player);
        return session::isExpired;
    }
    
    public static PlayerSession getOrStart(Plugin plugin, Player player)
    {
        return get(plugin, player).orElseGet(() -> start(plugin, player));
    }
    
    public static void end(Plugin plugin, Player player)
    {
        player.removeMetadata(KEY, plugin);
    }
    
    private final WeakReference<Plugin> pluginReference;
    private final WeakReference<Player> playerReference;
    
    private PlayerSession(Plugin plugin, Player player)
    {
        this.pluginReference = new WeakReference<>(plugin);
        this.playerReference = new WeakReference<>(player);
    }
    
    public boolean pluginIsValid()
    {
        @NullOr Plugin plugin = pluginReference.get();
        return plugin != null && plugin.isEnabled();
    }
    
    public Plugin plugin()
    {
        @NullOr Plugin plugin = pluginReference.get();
        if (plugin != null) { return plugin; }
        throw new IllegalStateException("Plugin has unloaded.");
    }
    
    public Optional<Player> player() { return Optional.ofNullable(playerReference.get()); }
    
    public void end()
    {
        @NullOr Player player = player().orElse(null);
        if (player == null || !pluginIsValid()) { return; }
        
        if (PlayerSession.get(plugin(), player).filter(this::equals).isPresent())
        {
            player.removeMetadata(KEY, plugin());
        }
    }
    
    public boolean isActive()
    {
        @NullOr Player player = player().orElse(null);
        return player != null && player.isOnline() && pluginIsValid() &&
            this.equals(PlayerSession.get(plugin(), player).orElse(null));
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
