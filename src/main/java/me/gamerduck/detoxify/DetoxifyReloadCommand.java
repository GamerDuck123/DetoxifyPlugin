package me.gamerduck.detoxify;

import me.gamerduck.detoxify.api.Config;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class DetoxifyReloadCommand implements CommandExecutor {

    DetoxifyPlugin plugin;
    public DetoxifyReloadCommand(DetoxifyPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (sender.hasPermission("detoxify.reload")) {
            Config.load(plugin.pluginFolder().resolve("values.properties"));
        }
        return false;
    }
}
