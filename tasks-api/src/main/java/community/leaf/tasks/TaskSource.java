/*
 * Copyright © 2021, RezzedUp <https://github.com/LeafCommunity/Tasks>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package community.leaf.tasks;

public interface TaskSource<T, B extends ScheduledTaskBuilder<T, B, ? extends Pending<B>>>
{
    TaskScheduler<T> getTaskScheduler();
    
    ScheduledTaskBuilder.Constructor<T, B> getTaskBuilderConstructor();
    
    interface Async<T, B extends ScheduledTaskBuilder<T, B, ? extends Pending<B>>>
        extends TaskSource<T, B>
    {
        default B async()
        {
            return getTaskBuilderConstructor().construct(getTaskScheduler(), Concurrency.ASYNC);
        }
    }
    
    interface Sync<T, B extends ScheduledTaskBuilder<T, B, ? extends Pending<B>>>
        extends TaskSource<T, B>
    {
        default B sync()
        {
            return getTaskBuilderConstructor().construct(getTaskScheduler(), Concurrency.SYNC);
        }
    }
    
    interface Concurrent<T, B extends ScheduledTaskBuilder<T, B, ? extends Pending<B>>>
        extends Async<T, B>, Sync<T, B> {};
}
