/*
 * Copyright © 2021, RezzedUp <https://github.com/LeafCommunity/Tasks>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package community.leaf.tasks;

public interface TaskBuilder<T> extends ScheduledTaskBuilder<T, TaskBuilder<T>, Pending<TaskBuilder<T>>>
{
    static <T> TaskBuilder<T> builder(TaskScheduler<T> scheduler, Concurrency concurrency)
    {
        return new TaskBuilderImpl<>(scheduler, concurrency);
    }
}
