/*
 * Copyright (c) 2021 SkillCode
 *
 * This file is a part of the source code of the
 * AdvancedMLGRush plugin by SkillCode.
 *
 * This file may only be used in compliance with the
 * LICENSE.txt (https://github.com/SkillC0de/AdvancedMLGRush/blob/master/LICENSE.txt).
 *
 * Support: https://discord.skillplugins.com
 */

package net.skillcode.advancedmlgrush.command.commands;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.config.configs.MainConfig;
import net.skillcode.advancedmlgrush.config.configs.MessageConfig;
import net.skillcode.advancedmlgrush.game.buildmode.BuildModeManager;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;

@Singleton
public class BuildCommand implements CommandExecutor {

    @Inject
    private BuildModeManager buildModeManager;
    @Inject
    private MainConfig mainConfig;
    @Inject
    private MessageConfig messageConfig;

    @Override
    public boolean onCommand(final CommandSender commandSender, final Command command, final String s, final String[] strings) {
        if (!(commandSender instanceof Player)) return false;

        final Player player = (Player) commandSender;
        final Optional<Player> optionalPlayer = Optional.of(player);

        if (!player.hasPermission(mainConfig.getString(MainConfig.ADMIN_PERMISSION))) {
            player.sendMessage(messageConfig.getWithPrefix(optionalPlayer, MessageConfig.NO_PERMISSION));
            return false;
        }

        if (!buildModeManager.isRegistered(player)) {
            buildModeManager.register(player);
        }

        final boolean isInBuildMode = buildModeManager.isInBuildMode(player);

        if (isInBuildMode) {
            player.sendMessage(messageConfig.getWithPrefix(optionalPlayer, MessageConfig.BUILD_MODE_OFF));
            player.setGameMode(GameMode.SURVIVAL);
            buildModeManager.setBuildMode(player, false);
        } else {
            player.sendMessage(messageConfig.getWithPrefix(optionalPlayer, MessageConfig.BUILD_MODE_ON));
            player.setGameMode(GameMode.CREATIVE);
            buildModeManager.setBuildMode(player, true);
        }

        return false;
    }
}
