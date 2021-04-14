package community.leaf.tasks.bukkit;

@FunctionalInterface
public interface PlayerSessionSource extends PluginSource
{
    default PlayerSessionDelegate sessions() { return this::plugin; }
}
