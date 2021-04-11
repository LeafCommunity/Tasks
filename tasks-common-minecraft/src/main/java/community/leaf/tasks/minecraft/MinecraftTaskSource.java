package community.leaf.tasks.minecraft;

import community.leaf.tasks.TaskBuilderConstructor;
import community.leaf.tasks.TaskBuilderSource;

@FunctionalInterface
public interface MinecraftTaskSource extends TaskBuilderSource<MinecraftTaskBuilder>
{
    @Override
    default TaskBuilderConstructor<MinecraftTaskBuilder> getTaskBuilderConstructor()
    {
        return MinecraftTaskBuilder::new;
    }
}
