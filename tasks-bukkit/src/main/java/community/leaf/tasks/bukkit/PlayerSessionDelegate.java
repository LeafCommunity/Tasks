/*
 * Copyright © 2021, RezzedUp <https://github.com/LeafCommunity/Tasks>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package community.leaf.tasks.bukkit;

import community.leaf.tasks.Unless;
import org.bukkit.entity.Player;

import java.util.Optional;

@SuppressWarnings({"UnusedReturnValue", "unused"})
@FunctionalInterface
public interface PlayerSessionDelegate extends PluginSource
{
    default PlayerSession start(Player player) { return PlayerSession.start(plugin(), player); }
    
    default Optional<PlayerSession> get(Player player) { return PlayerSession.get(plugin(), player); }
    
    default PlayerSession getOrStart(Player player) { return PlayerSession.getOrStart(plugin(), player); }
    
    default void end(Player player) { PlayerSession.end(plugin(), player); }
    
    default Unless expired(Player player) { return PlayerSession.expired(plugin(), player); }
}
