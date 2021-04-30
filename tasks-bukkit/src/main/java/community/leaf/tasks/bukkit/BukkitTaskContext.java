/*
 * Copyright © 2021, RezzedUp <https://github.com/LeafCommunity/Tasks>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package community.leaf.tasks.bukkit;

import community.leaf.tasks.AbstractTaskContext;
import community.leaf.tasks.Concurrency;
import community.leaf.tasks.Schedule;
import org.bukkit.scheduler.BukkitTask;

final class BukkitTaskContext extends AbstractTaskContext<BukkitTask>
{
    BukkitTaskContext(Schedule schedule) { super(schedule); }
    
    @Override
    public boolean isCancelled() { return task().isCancelled(); }
    
    @Override
    public void cancel() { task().cancel(); }
    
    @Override
    public Concurrency concurrency()
    {
        return (task().isSync()) ? Concurrency.SYNC : Concurrency.ASYNC;
    }
}
