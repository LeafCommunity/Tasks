/*
 * Copyright © 2021, RezzedUp <https://github.com/LeafCommunity/Tasks>
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
    
    T runNow(Concurrency concurrency, Runnable runnable);
    
    T runFuture(Concurrency concurrency, Runnable runnable, long delay);
    
    T runRepeating(Concurrency concurrency, Runnable runnable, long delay, long period);
    
    default TaskContext<T> schedule(Schedule schedule, ContextualRunnable<T> runnable)
    {
        TaskContext<T> context = context(schedule);
        
        Runnable task = () -> {
            runnable.run(context);
            context.iterate();
        };
        
        if (schedule.repeats().until() != Repeats.NEVER)
        {
            context.task(runRepeating(schedule.concurrency(), task, schedule.delay(), schedule.period()));
        }
        else if (schedule.isDelayed())
        {
            context.task(runFuture(schedule.concurrency(), task, schedule.delay()));
        }
        else
        {
            context.task(runNow(schedule.concurrency(), task));
        }
        
        return context;
    }
}
