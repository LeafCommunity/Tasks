package community.leaf.tasks.bukkit;

import community.leaf.tasks.ConcurrentTaskSource;
import community.leaf.tasks.TaskBuilderConstructor;
import community.leaf.tasks.minecraft.MinecraftTaskBuilder;

@FunctionalInterface
public interface BukkitTaskSource extends ConcurrentTaskSource<MinecraftTaskBuilder>, PlayerSessionSource, PluginSource
{
    @Override
    default BukkitTaskScheduler getTaskScheduler()
    {
        return this::plugin;
    }
    
    @Override
    default TaskBuilderConstructor<MinecraftTaskBuilder> getTaskBuilderConstructor()
    {
        return MinecraftTaskBuilder::new;
    }
}
