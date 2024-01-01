/*
 * Copyright © 2021-2024, RezzedUp <https://github.com/LeafCommunity/Tasks>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package community.leaf.tasks;

import java.util.List;

@FunctionalInterface
public interface Unless
{
    boolean criteria();
    
    static boolean always() { return true; }
    
    static boolean never() { return false; }
    
    static Unless any(List<Unless> caveats) { return () -> caveats.stream().anyMatch(Unless::criteria); }
    
    static Builder<?> builder() { return new UnlessBuilderImpl(); }
    
    interface Source
    {
        Unless unless();
    }
    
    interface Builder<B extends Builder<B>> extends Source
    {
        B unless(Unless criteria);
    }
}
