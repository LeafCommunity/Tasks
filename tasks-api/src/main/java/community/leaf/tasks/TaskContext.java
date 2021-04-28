/*
 * Copyright © 2021, RezzedUp <https://github.com/LeafCommunity/Tasks>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package community.leaf.tasks;

@SuppressWarnings("unused")
public interface TaskContext<T> extends Schedule.Source
{
    Concurrency concurrency();
    
    long iterations();
    
    void iterate();
    
    boolean isCancelled();
    
    void cancel();
    
    T task();
    
    void task(T task);
    
    default boolean isRepeatingForever() { return schedule().repeats().until() == Repeats.FOREVER; }
    
    default boolean isRepeatingFinitely() { return schedule().repeats().until() == Repeats.FINITE; }
    
    default boolean isRepeating() { return schedule().repeats().until() != Repeats.NEVER; }
    
    default boolean isDoneRepeating()
    {
        Repeats.Expected repeats = schedule().repeats();
        return (isCancelled())
            || (repeats.until() == Repeats.NEVER && iterations() > repeats.repetitions())
            || (repeats.until() == Repeats.FINITE && iterations() >= repeats.repetitions());
    }
    
    default boolean isFirstIteration() { return iterations() == 0; }
    
    default boolean isLastIteration()
    {
        Repeats.Expected repeats = schedule().repeats();
        return (repeats.until() == Repeats.NEVER)
            || (repeats.until() == Repeats.FINITE && iterations() == repeats.repetitions() - 1);
    }
}
