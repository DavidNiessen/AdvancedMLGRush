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
import net.skillcode.advancedmlgrush.config.configs.MessageConfig;
import net.skillcode.advancedmlgrush.game.map.MapInstance;
import net.skillcode.advancedmlgrush.game.map.MapInstanceManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;

public class QuitCommand implements CommandExecutor {

    @Inject
    private MapInstanceManager mapInstanceManager;
    @Inject
    private MessageConfig messageConfig;

    @Override
    public boolean onCommand(final CommandSender commandSender, final Command command, final String s, final String[] strings) {
        if (!(commandSender instanceof Player)) return false;

        final Player player = (Player) commandSender;
        final Optional<Player> optionalPlayer = Optional.of(player);

        final Optional<MapInstance> optionalMapInstance = mapInstanceManager.getMapInstance(player);

        if (!optionalMapInstance.isPresent()) {
            player.sendMessage(messageConfig.getWithPrefix(optionalPlayer, MessageConfig.CANNOT_USE_COMMAND));
        } else {
            optionalMapInstance.get().quitMap(player);
        }
        return false;
    }
}
