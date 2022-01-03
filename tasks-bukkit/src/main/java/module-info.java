/*
 * Copyright © 2021-2022, RezzedUp <https://github.com/LeafCommunity/Tasks>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
module community.leaf.tasks.bukkit
{
    requires transitive community.leaf.tasks.minecraft;
    requires static pl.tlinkowski.annotation.basic;
    requires org.bukkit;
    
    exports community.leaf.tasks.bukkit;
}