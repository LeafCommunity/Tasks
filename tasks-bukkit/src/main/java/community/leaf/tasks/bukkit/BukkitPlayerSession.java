/*
 * Copyright © 2021, RezzedUp <https://github.com/LeafCommunity/Tasks>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package community.leaf.tasks.bukkit;

import community.leaf.tasks.Unless;
import community.leaf.tasks.minecraft.PlayerSession;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import pl.tlinkowski.annotation.basic.NullOr;

import java.lang.ref.WeakReference;
import java.util.Objects;
import java.util.Optional;

final class BukkitPlayerSession implements PlayerSession
{
    static final String KEY = BukkitPlayerSession.class.getName();
    
    private static void validate(Plugin plugin, Player player)
    {
        Objects.requireNonNull(plugin, "plugin");
        Objects.requireNonNull(player, "player");
    }
    
    static BukkitPlayerSession start(Plugin plugin, Player player)
    {
        validate(plugin, player);
        
        BukkitPlayerSession session = new BukkitPlayerSession(plugin, player);
        player.setMetadata(KEY, new FixedMetadataValue(plugin, session));
        return session;
    }
    
    static Optional<BukkitPlayerSession> get(Plugin plugin, Player player)
    {
        validate(plugin, player);
        
        for (MetadataValue meta : player.getMetadata(KEY))
        {
            if (!plugin.equals(meta.getOwningPlugin())) { continue; }
            
            @NullOr Object value = meta.value();
            if (!(value instanceof BukkitPlayerSession)) { continue; }
            
            return Optional.of((BukkitPlayerSession) value);
        }
        
        return Optional.empty();
    }
    
    static BukkitPlayerSession getOrStart(Plugin plugin, Player player)
    {
        // validated in get() and start()
        return get(plugin, player).orElseGet(() -> start(plugin, player));
    }
    
    static void end(Plugin plugin, Player player)
    {
        validate(plugin, player);
        player.removeMetadata(KEY, plugin);
    }
    
    static Unless expired(Plugin plugin, Player player)
    {
        // validated in getOrStart()
        BukkitPlayerSession session = getOrStart(plugin, player);
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
    
    private BukkitPlayerSession(Plugin plugin, Player player)
    {
        this.pluginReference = new WeakReference<>(plugin);
        this.playerReference = new WeakReference<>(player);
    }
    
    @NullOr Plugin plugin() { return pluginReference.get(); }
    
    @NullOr Player player() { return playerReference.get(); }
    
    @Override
    public void end()
    {
        @NullOr Plugin plugin = plugin();
        @NullOr Player player = player();
        
        if (player == null || !isPluginValid(plugin)) { return; }
        if (BukkitPlayerSession.get(plugin, player).filter(this::equals).isEmpty()) { return; }
        
        player.removeMetadata(KEY, plugin);
    }
    
    @Override
    public boolean isActive()
    {
        @NullOr Plugin plugin = plugin();
        @NullOr Player player = player();
        
        return isPlayerOnline(player) && isPluginValid(plugin) &&
            BukkitPlayerSession.get(plugin, player).filter(this::equals).isPresent();
    }
    
    @Override
    public String toString()
    {
        return "PlayerSession{" +
            "pluginReference=" + pluginReference +
            ", playerReference=" + playerReference +
            "}@" + Integer.toHexString(hashCode());
    }
}
