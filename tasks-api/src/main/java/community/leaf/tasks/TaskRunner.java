/*
 * Copyright © 2021-2024, RezzedUp <https://github.com/LeafCommunity/Tasks>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package community.leaf.tasks;

@SuppressWarnings("UnusedReturnValue")
public interface TaskRunner<T>
{
    TaskContext<T> run(ContextualRunnable<T> runnable);
    
    default TaskContext<T> run(Runnable runnable)
    {
        return run(task -> runnable.run());
    }
}
