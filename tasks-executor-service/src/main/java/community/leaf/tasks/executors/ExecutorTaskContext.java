/*
 * Copyright © 2021, RezzedUp <https://github.com/LeafCommunity/Tasks>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package community.leaf.tasks.executors;

import community.leaf.tasks.Concurrency;
import community.leaf.tasks.Repeats;
import community.leaf.tasks.WrappedTaskContext;

import java.util.concurrent.Future;

public class ExecutorTaskContext extends WrappedTaskContext<Future<?>>
{
    private final Concurrency concurrency;
    
    public ExecutorTaskContext(Concurrency concurrency, Repeats.Expected repeats)
    {
        super(repeats);
        this.concurrency = concurrency;
    }
    
    @Override
    public Concurrency concurrency()
    {
        return concurrency;
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
