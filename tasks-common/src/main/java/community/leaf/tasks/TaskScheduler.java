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
    WrappedTaskContext<T> createTaskContext(Repeats.Expected repetitions);
    
    T runTask(Concurrency concurrency, Runnable runnable);
    
    T runFutureTask(Concurrency concurrency, Runnable runnable, long delay);
    
    T runRepeatingTask(Concurrency concurrency, Runnable runnable, long delay, long period);
    
    default T schedule(Schedulable when, Runnable runnable)
    {
        if (when.repeats().until() != Repeats.NEVER)
        {
            return runRepeatingTask(when.concurrency(), runnable, when.delay(), when.period());
        }
        else if (when.isDelayed())
        {
            return runFutureTask(when.concurrency(), runnable, when.delay());
        }
        else
        {
            return runTask(when.concurrency(), runnable);
        }
    }
    
    default T schedule(Schedulable when, ContextualRunnable runnable)
    {
        WrappedTaskContext<T> context = createTaskContext(when.repeats());
        
        T task = schedule(when, () -> {
            runnable.run(context);
            context.iterate();
        });
        
        return context.wrap(task);
    }
}
