/*
 * Copyright © 2021, RezzedUp <https://github.com/LeafCommunity/Tasks>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package community.leaf.tasks;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

final class UnlessBuilderImpl implements Unless.Builder<UnlessBuilderImpl>
{
    private final List<Unless> caveats = new ArrayList<>();
    
    @Override
    public UnlessBuilderImpl unless(Unless criteria)
    {
        caveats.add(Objects.requireNonNull(criteria, "criteria"));
        return this;
    }
    
    @Override
    public Unless unless()
    {
        if (caveats.isEmpty()) { return Unless::never; }
        List<Unless> copy = List.copyOf(caveats);
        return Unless.any(copy);
    }
}
