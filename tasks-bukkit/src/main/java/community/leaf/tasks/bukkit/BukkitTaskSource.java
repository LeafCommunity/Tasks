package community.leaf.tasks.bukkit;

import community.leaf.tasks.minecraft.MinecraftTaskSource;

@FunctionalInterface
public interface BukkitTaskSource extends MinecraftTaskSource, PlayerSessionSource, PluginProvider
{
    default BukkitTaskScheduler getTaskScheduler() { return this::plugin; }
}
