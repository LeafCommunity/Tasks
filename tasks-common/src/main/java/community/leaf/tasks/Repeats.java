package community.leaf.tasks;

import java.util.function.Predicate;

public enum Repeats
{
    NEVER(reps -> reps == 0),
    FINITE(reps -> reps >= 1),
    FOREVER(reps -> reps <= -1);
    
    private final Predicate<Long> validity;
    
    Repeats(Predicate<Long> validity) { this.validity = validity; }
    
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
    }
    
    public static class Constant
    {
        private Constant() { throw new UnsupportedOperationException(); }
        
        public static final Expected NEVER = Repeats.NEVER.expecting(0);
        
        public static final Expected FOREVER = Repeats.FOREVER.expecting(-1);
    }
    
    public static Expected from(long repetitions)
    {
        if (NEVER.validity.test(repetitions)) { return Constant.NEVER; }
        if (FOREVER.validity.test(repetitions)) { return Constant.FOREVER; }
        return FINITE.expecting(repetitions);
    }
}