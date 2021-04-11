package community.leaf.tasks.bukkit;

import community.leaf.tasks.Concurrency;
import community.leaf.tasks.Repeats;
import community.leaf.tasks.WrappedTaskContext;
import org.bukkit.scheduler.BukkitTask;

public class BukkitTaskContext extends WrappedTaskContext<BukkitTask>
{
    public BukkitTaskContext(Repeats.Expected repetitions) { super(repetitions); }
    
    @Override
    public boolean isCancelled() { return getTask().isCancelled(); }
    
    @Override
    public void cancel() { getTask().cancel(); }
    
    @Override
    public Concurrency getConcurrency()
    {
        return (getTask().isSync()) ? Concurrency.SYNC : Concurrency.ASYNC;
    }
}
