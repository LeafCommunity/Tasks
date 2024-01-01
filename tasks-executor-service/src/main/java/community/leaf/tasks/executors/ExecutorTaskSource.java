/*
 * Copyright © 2021-2024, RezzedUp <https://github.com/LeafCommunity/Tasks>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package community.leaf.tasks.executors;

import community.leaf.tasks.ScheduledTaskBuilder;
import community.leaf.tasks.TaskBuilder;
import community.leaf.tasks.TaskSource;

import java.util.concurrent.Future;

@FunctionalInterface
public interface ExecutorTaskSource extends ExecutorServiceSource, TaskSource<Future<?>, TaskBuilder<Future<?>>>
{
    @Override
    default ExecutorTaskScheduler getTaskScheduler()
    {
        return this::executor;
    }
    
    @Override
    default ScheduledTaskBuilder.Constructor<Future<?>, TaskBuilder<Future<?>>> getTaskBuilderConstructor()
    {
        return TaskBuilder::builder;
    }
    
    @FunctionalInterface
    interface Async extends ExecutorTaskSource, TaskSource.Async<Future<?>, TaskBuilder<Future<?>>> {}
    
    @FunctionalInterface
    interface Sync extends ExecutorTaskSource, TaskSource.Sync<Future<?>, TaskBuilder<Future<?>>> {}
    
    @FunctionalInterface
    interface Concurrent extends ExecutorTaskSource, TaskSource.Concurrent<Future<?>, TaskBuilder<Future<?>>> {}
}
