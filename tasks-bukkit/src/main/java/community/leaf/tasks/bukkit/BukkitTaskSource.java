package community.leaf.tasks.bukkit;

import community.leaf.tasks.TaskSource;

@FunctionalInterface
public interface BukkitTaskSource extends TaskSource
{
    BukkitTaskScheduler getTaskScheduler();
}
