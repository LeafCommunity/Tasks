/*
 * Copyright © 2021, RezzedUp <https://github.com/LeafCommunity/Tasks>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package community.leaf.tasks;

import pl.tlinkowski.annotation.basic.NullOr;

import java.util.Objects;
import java.util.function.LongPredicate;

public enum Repeats
{
    NEVER(reps -> reps == 0),
    FINITE(reps -> reps >= 1),
    FOREVER(reps -> reps <= -1);
    
    public static Expected expect(long repetitions)
    {
        if (NEVER.validity.test(repetitions)) { return Constantly.NEVER; }
        if (FOREVER.validity.test(repetitions)) { return Constantly.FOREVER; }
        return FINITE.expecting(repetitions);
    }
    
    private final LongPredicate validity;
    
    Repeats(LongPredicate validity) { this.validity = validity; }
    
    public Expected expecting(long repetitions)
    {
        if (validity.test(repetitions)) { return new Expected(repetitions); }
        throw new IllegalArgumentException("Invalid repetitions for " + name() + ": " + repetitions);
    }
    
    public class Expected
    {
        private final long repetitions;
        
        Expected(long repetitions) { this.repetitions = repetitions; }
        
        public long repetitions() { return repetitions; }
        
        public Repeats until() { return Repeats.this; }
    
        @Override
        public String toString()
        {
            return "Expected{" +
                "repetitions=" + repetitions +
                ", until=" + until() +
                '}';
        }
    
        @Override
        public boolean equals(@NullOr Object o)
        {
            if (this == o) { return true; }
            if (o == null || getClass() != o.getClass()) { return false; }
            
            Expected that = (Expected) o;
            return repetitions == that.repetitions && until() == that.until();
        }
    
        @Override
        public int hashCode()
        {
            return Objects.hash(repetitions, Repeats.this);
        }
    }
    
    public static class Constantly
    {
        private Constantly() { throw new UnsupportedOperationException(); }
        
        public static final Expected NEVER = Repeats.NEVER.expecting(0);
        
        public static final Expected FOREVER = Repeats.FOREVER.expecting(-1);
    }
}
