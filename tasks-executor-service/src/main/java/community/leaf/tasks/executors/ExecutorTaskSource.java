/*
 * Copyright © 2021, RezzedUp <https://github.com/LeafCommunity/Tasks>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package community.leaf.tasks.executors;

import community.leaf.tasks.TaskBuilder;
import community.leaf.tasks.TaskBuilderConstructor;
import community.leaf.tasks.TaskBuilderSource;

@FunctionalInterface
public interface ExecutorTaskSource extends ExecutorServiceSource, TaskBuilderSource<TaskBuilder>
{
    @Override
    default ExecutorTaskScheduler getTaskScheduler()
    {
        return this::executor;
    }
    
    @Override
    default TaskBuilderConstructor<TaskBuilder> getTaskBuilderConstructor()
    {
        return TaskBuilder::new;
    }
}
