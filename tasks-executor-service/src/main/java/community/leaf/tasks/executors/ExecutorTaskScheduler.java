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
import community.leaf.tasks.TaskScheduler;
import community.leaf.tasks.WrappedTaskContext;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@FunctionalInterface
public interface ExecutorTaskScheduler extends ExecutorServiceSource, TaskScheduler<Future<?>>
{
    @Override
    default WrappedTaskContext<Future<?>> createTaskContext(Concurrency concurrency, Repeats.Expected repeats)
    {
        return new ExecutorTaskContext(concurrency, repeats);
    }
    
    @Override
    default Future<?> runTask(Concurrency concurrency, Runnable runnable)
    {
        return executor(concurrency).submit(runnable);
    }
    
    @Override
    default Future<?> runFutureTask(Concurrency concurrency, Runnable runnable, long delay)
    {
        return executor(concurrency).schedule(runnable, delay, TimeUnit.MILLISECONDS);
    }
    
    @Override
    default Future<?> runRepeatingTask(Concurrency concurrency, Runnable runnable, long delay, long period)
    {
        return executor(concurrency).scheduleAtFixedRate(runnable, delay, period, TimeUnit.MILLISECONDS);
    }
}