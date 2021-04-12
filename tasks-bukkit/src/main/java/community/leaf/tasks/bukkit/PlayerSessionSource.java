package community.leaf.tasks.bukkit;

@FunctionalInterface
public interface PlayerSessionSource extends PluginProvider
{
    default PlayerSessionDelegate sessions() { return this::plugin; }
}
