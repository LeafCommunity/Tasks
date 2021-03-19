package community.leaf.tasks.bukkit;

import community.leaf.tasks.Concurrency;
import community.leaf.tasks.WrappedTaskContext;
import org.bukkit.scheduler.BukkitTask;

public class BukkitTaskContext extends WrappedTaskContext<BukkitTask>
{
    public BukkitTaskContext(long repetitions) { super(repetitions); }
    
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
