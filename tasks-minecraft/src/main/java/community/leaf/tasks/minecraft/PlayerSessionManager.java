/*
 * Copyright © 2021-2022, RezzedUp <https://github.com/LeafCommunity/Tasks>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package community.leaf.tasks.minecraft;

import community.leaf.tasks.Unless;

import java.util.Optional;

public interface PlayerSessionManager<P>
{
    PlayerSession start(P player);
    
    Optional<PlayerSession> get(P player);
    
    PlayerSession getOrStart(P player);
    
    void end(P player);
    
    void endAll();
    
    Unless expired(P player);
    
    interface Source<P>
    {
        PlayerSessionManager<P> sessions();
    }
}
