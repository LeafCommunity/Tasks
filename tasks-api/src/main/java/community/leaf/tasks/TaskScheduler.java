/*
 * Copyright © 2021-2024, RezzedUp <https://github.com/LeafCommunity/Tasks>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package community.leaf.tasks;

@SuppressWarnings("UnusedReturnValue")
public interface TaskScheduler<T>
{
    TaskContext<T> context(Schedule schedule);
    
    T now(Concurrency concurrency, Runnable runnable);
    
    T future(Concurrency concurrency, Runnable runnable, long delay);
    
    T repeat(Concurrency concurrency, Runnable runnable, long delay, long period);
    
    default TaskContext<T> schedule(Schedule schedule, ContextualRunnable<T> runnable)
    {
        TaskContext<T> context = context(schedule);
        
        Runnable task = () -> {
            runnable.run(context);
            context.iterate();
        };
        
        if (schedule.repeats().until() != Repeats.NEVER)
        {
            context.task(repeat(schedule.concurrency(), task, schedule.delay(), schedule.period()));
        }
        else if (schedule.isDelayed())
        {
            context.task(future(schedule.concurrency(), task, schedule.delay()));
        }
        else
        {
            context.task(now(schedule.concurrency(), task));
        }
        
        return context;
    }
}
