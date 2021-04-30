/*
 * Copyright © 2021, RezzedUp <https://github.com/LeafCommunity/Tasks>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package community.leaf.tasks;

public interface ScheduledTaskBuilder<T, B extends ScheduledTaskBuilder<T, B, P>, P extends Pending<B>>
    extends Schedule.Builder<B, P>, TaskRunner<T>, Unless.Builder<B>
{
    interface Constructor<T, B extends ScheduledTaskBuilder<T, B, ? extends Pending<B>>>
    {
        B construct(TaskScheduler<T> scheduler, Concurrency concurrency);
    }
}
