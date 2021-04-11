package community.leaf.tasks.minecraft;

import java.util.concurrent.TimeUnit;

public class Ticks
{
    private Ticks() { throw new UnsupportedOperationException(); }
    
    /**
     * One second's worth of Minecraft ticks.
     */
    public static final long SECOND = from(1, TimeUnit.SECONDS);
    
    /**
     * One minute's worth of Minecraft ticks.
     */
    public static final long MINUTE = from(1, TimeUnit.MINUTES);
    
    /**
     * One hour's worth of Minecraft ticks.
     */
    public static final long HOUR = from(1, TimeUnit.HOURS);
    
    /**
     * Convert from a duration of time to Minecraft ticks.
     *
     * @param sourceDuration    duration of time units
     * @param sourceUnit        unit of time
     * @return  ticks equivalent of the duration
     */
    public static long from(long sourceDuration, TimeUnit sourceUnit)
    {
        // 1 tick = 50 milliseconds
        return TimeUnit.MILLISECONDS.convert(sourceDuration, sourceUnit) / 50;
    }
    
    /**
     * Convert from a duration of time to Minecraft ticks.
     *
     * @param sourceDuration    duration of integer time units
     * @param sourceUnit        unit of time
     * @return  ticks equivalent of the duration as an integer
     */
    public static int fromInteger(int sourceDuration, TimeUnit sourceUnit)
    {
        return (int) from(sourceDuration, sourceUnit);
    }
    
    /**
     * Convert from Minecraft ticks to a duration of time.
     *
     * @param ticks number of ticks
     * @param unit  unit of time
     * @return      units of time equivalent to ticks
     */
    public static long into(long ticks, TimeUnit unit)
    {
        return unit.convert(ticks * 50, TimeUnit.MILLISECONDS);
    }
    
    /**
     * Convert from Minecraft ticks to a duration of time.
     *
     * @param ticks integer number of ticks
     * @param unit  unit of time
     * @return      units of time equivalent to ticks as an integer
     */
    public static int intoInteger(int ticks, TimeUnit unit)
    {
        return Long.valueOf(into(ticks, unit)).intValue();
    }
}
