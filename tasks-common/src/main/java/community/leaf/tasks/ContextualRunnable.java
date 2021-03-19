package community.leaf.tasks;

@FunctionalInterface
public interface ContextualRunnable
{
    void run(TaskContext task);
}
