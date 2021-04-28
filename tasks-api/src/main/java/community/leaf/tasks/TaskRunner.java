package community.leaf.tasks;

public interface TaskRunner<T>
{
    TaskContext<T> run(ContextualRunnable<T> runnable);
    
    default TaskContext<T> run(Runnable runnable)
    {
        return run(task -> runnable.run());
    }
}
