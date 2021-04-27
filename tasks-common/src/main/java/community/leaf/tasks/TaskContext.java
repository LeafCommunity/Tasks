/*
 * Copyright © 2021, RezzedUp <https://github.com/LeafCommunity/Tasks>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package community.leaf.tasks;

@SuppressWarnings("unused")
public interface TaskContext
{
    Concurrency concurrency();
    
    long iterations();
    
    Repeats.Expected repeats();
    
    boolean isCancelled();
    
    void cancel();
    
    default boolean isRepeatingForever() { return repeats().until() == Repeats.FOREVER; }
    
    default boolean isRepeatingFinitely() { return repeats().until() == Repeats.FINITE; }
    
    default boolean isRepeating() { return repeats().until() != Repeats.NEVER; }
    
    default boolean isDoneRepeating()
    {
        Repeats.Expected repeats = repeats();
        return (isCancelled())
            || (repeats.until() == Repeats.NEVER && iterations() > repeats.repetitions())
            || (repeats.until() == Repeats.FINITE && iterations() >= repeats.repetitions());
    }
    
    default boolean isFirstIteration() { return iterations() == 0; }
    
    default boolean isLastIteration()
    {
        Repeats.Expected repeats = repeats();
        return (repeats.until() == Repeats.NEVER)
            || (repeats.until() == Repeats.FINITE && iterations() == repeats.repetitions() - 1);
    }
}
