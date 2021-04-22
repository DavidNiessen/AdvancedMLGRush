package net.skillcode.advancedmlgrush.command;

import com.google.inject.Inject;
import net.skillcode.advancedmlgrush.config.configs.MessageConfig;
import net.skillcode.advancedmlgrush.game.buildmode.BuildModeManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BuildCommand implements CommandExecutor {

    private final BuildModeManager buildModeManager;
    private final MessageConfig messageConfig;

    @Inject
    public BuildCommand(final @NotNull BuildModeManager buildModeManager, final @NotNull MessageConfig messageConfig) {
        this.buildModeManager = buildModeManager;
        this.messageConfig = messageConfig;
    }

    @Override
    public boolean onCommand(final CommandSender commandSender, final Command command, final String s, final String[] strings) {
        if (!(commandSender instanceof Player)) return false;

        final Player player = (Player) commandSender;

        if (!buildModeManager.isRegistered(player)) {
            buildModeManager.register(player);
        }

        final boolean isInBuildMode = buildModeManager.isInBuildMode(player);

        if (isInBuildMode) {
            player.sendMessage(messageConfig.getWithPrefix(MessageConfig.BUILD_MODE_OFF));
            buildModeManager.setBuildMode(player, false);
        } else {
            player.sendMessage(messageConfig.getWithPrefix(MessageConfig.BUILD_MODE_ON));
            buildModeManager.setBuildMode(player, true);
        }

        return false;
    }
}
