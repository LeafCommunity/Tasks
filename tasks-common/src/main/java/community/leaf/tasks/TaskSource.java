package community.leaf.tasks;

@FunctionalInterface
public interface TaskSource extends TaskBuilderSource<TaskBuilder>
{
    @Override
    default TaskBuilderConstructor<TaskBuilder> getTaskBuilderConstructor() { return TaskBuilder::new; }
}
