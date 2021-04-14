package community.leaf.tasks.bukkit;

import org.bukkit.plugin.Plugin;

@FunctionalInterface
public interface PluginSource
{
    Plugin plugin();
}
