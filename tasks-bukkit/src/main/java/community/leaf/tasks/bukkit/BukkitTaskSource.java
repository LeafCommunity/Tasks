package community.leaf.tasks.bukkit;

import community.leaf.tasks.minecraft.MinecraftTaskSource;

@FunctionalInterface
public interface BukkitTaskSource extends MinecraftTaskSource
{
    BukkitTaskScheduler getTaskScheduler();
}
