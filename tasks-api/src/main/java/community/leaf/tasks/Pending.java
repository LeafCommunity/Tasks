/*
 * Copyright © 2021, RezzedUp <https://github.com/LeafCommunity/Tasks>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package community.leaf.tasks;

import java.util.function.LongFunction;

@SuppressWarnings("unused")
public interface Pending<B>
{
    static <B> Pending<B> milliseconds(LongFunction<B> function, long units)
    {
        return new AbstractPending<>(function, units) {};
    }
    
    B milliseconds();
    
    B seconds();
    
    B minutes();
    
    B hours();
    
    B days();
    
    interface Constructor<B, P extends Pending<B>>
    {
        P construct(LongFunction<B> function, long units);
    }
}
