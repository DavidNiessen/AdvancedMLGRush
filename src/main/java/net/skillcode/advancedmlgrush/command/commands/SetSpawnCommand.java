/*
 * Copyright (c) 2021 SkillCode
 *
 * This file is a part of the source code of the
 * AdvancedMLGRush plugin by SkillCode.
 *
 * This class may only be used in compliance with the
 * LICENSE.txt (https://github.com/SkillC0de/AdvancedMLGRush/blob/master/LICENSE.txt).
 *
 * Support: https://discord.skillplugins.com
 */

package net.skillcode.advancedmlgrush.command.commands;

import com.google.inject.Inject;
import net.skillcode.advancedmlgrush.config.configs.MainConfig;
import net.skillcode.advancedmlgrush.config.configs.MessageConfig;
import net.skillcode.advancedmlgrush.game.spawn.SpawnFile;
import net.skillcode.advancedmlgrush.game.spawn.SpawnFileLoader;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;

public class SetSpawnCommand implements CommandExecutor {

    @Inject
    private SpawnFileLoader spawnFileLoader;
    @Inject
    private MessageConfig messageConfig;
    @Inject
    private MainConfig mainConfig;


    @Override
    public boolean onCommand(final CommandSender commandSender, final Command command, final String s, final String[] strings) {
        if (!(commandSender instanceof Player)) return false;

        final Player player = (Player) commandSender;
        final Optional<Player> optionalPlayer = Optional.of(player);

        if (!player.hasPermission(mainConfig.getString(MainConfig.ADMIN_PERMISSION))) {
            player.sendMessage(messageConfig.getWithPrefix(optionalPlayer, MessageConfig.NO_PERMISSION));
            return false;
        }

        final Location location = player.getLocation();
        spawnFileLoader.createSpawnFile(new SpawnFile(player.getWorld().getName(), location.getX(),
                location.getY(), location.getZ(), location.getYaw(), location.getPitch()));

        player.sendMessage(messageConfig.getWithPrefix(optionalPlayer, MessageConfig.SPAWN_SET));
        return false;
    }
}
