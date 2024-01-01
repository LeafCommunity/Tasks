/*
 * Copyright © 2021-2024, RezzedUp <https://github.com/LeafCommunity/Tasks>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package community.leaf.tasks.executors;

import community.leaf.tasks.AbstractTaskContext;
import community.leaf.tasks.Concurrency;
import community.leaf.tasks.Schedule;

import java.util.concurrent.Future;

final class ExecutorTaskContext extends AbstractTaskContext<Future<?>>
{
    ExecutorTaskContext(Schedule schedule)
    {
        super(schedule);
    }
    
    @Override
    public Concurrency concurrency()
    {
        return schedule().concurrency();
    }
    
    @Override
    public boolean isCancelled()
    {
        return task().isDone();
    }
    
    @Override
    public void cancel()
    {
        task().cancel(false);
    }
}
