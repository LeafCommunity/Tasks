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
import community.leaf.tasks.minecraft.PlayerSessionManager;
import org.bukkit.entity.Player;

import java.util.Optional;

@SuppressWarnings({"UnusedReturnValue", "unused"})
@FunctionalInterface
public interface BukkitPlayerSessionManager extends PlayerSessionManager<Player>, PluginSource
{
    @Override
    default PlayerSession start(Player player)
    {
        return BukkitPlayerSession.start(plugin(), player);
    }
    
    @Override
    @SuppressWarnings("unchecked")
    default Optional<PlayerSession> get(Player player)
    {
        return (Optional<PlayerSession>) (Optional<? extends PlayerSession>) BukkitPlayerSession.get(plugin(), player);
    }
    
    @Override
    default PlayerSession getOrStart(Player player)
    {
        return BukkitPlayerSession.getOrStart(plugin(), player);
    }
    
    @Override
    default void end(Player player)
    {
        BukkitPlayerSession.end(plugin(), player);
    }
    
    @Override
    default void endAll()
    {
        BukkitPlayerSession.endAll(plugin());
    }
    
    @Override
    default Unless expired(Player player)
    {
        return BukkitPlayerSession.expired(plugin(), player);
    }
    
    interface Source extends PlayerSessionManager.Source<Player>, PluginSource
    {
        @Override
        default PlayerSessionManager<Player> sessions()
        {
            return (BukkitPlayerSessionManager) this::plugin;
        }
    }
}
