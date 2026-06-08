/*
 * Copyright © 2021-2026, RezzedUp <https://github.com/LeafCommunity/Tasks>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package community.leaf.tasks.minecraft;

import community.leaf.tasks.ScheduledTaskBuilder;

public interface MinecraftTaskBuilder<T> extends ScheduledTaskBuilder<T, MinecraftTaskBuilder<T>> {
	@Override
	Ticks<MinecraftTaskBuilder<T>> delay(long duration);
	
	@Override
	Ticks<MinecraftTaskBuilder<T>> every(long duration);
}
