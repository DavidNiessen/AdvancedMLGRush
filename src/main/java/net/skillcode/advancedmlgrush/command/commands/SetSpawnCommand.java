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
import net.skillcode.advancedmlgrush.config.configs.MessageConfig;
import net.skillcode.advancedmlgrush.game.spawn.SpawnFile;
import net.skillcode.advancedmlgrush.game.spawn.SpawnFileLoader;
import net.skillcode.advancedmlgrush.util.json.JsonLocation;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

@Singleton
public class SetSpawnCommand implements CommandExecutor {

    private final SpawnFileLoader spawnFileLoader;
    private final MessageConfig messageConfig;

    @Inject
    public SetSpawnCommand(final @NotNull SpawnFileLoader spawnFileLoader,
                           final @NotNull MessageConfig messageConfig) {
        this.spawnFileLoader = spawnFileLoader;
        this.messageConfig = messageConfig;
    }


    @Override
    public boolean onCommand(final CommandSender commandSender, final Command command, final String s, final String[] strings) {
        if (!(commandSender instanceof Player)) return false;

        final Player player = (Player) commandSender;
        final Optional<Player> optionalPlayer = Optional.of(player);

        final Location location = player.getLocation();
        spawnFileLoader.createSpawnFile(new SpawnFile(new JsonLocation(player.getWorld().getName(), location.getX(),
                location.getY(), location.getZ(), location.getYaw(), location.getPitch())));

        player.sendMessage(messageConfig.getWithPrefix(optionalPlayer, MessageConfig.SPAWN_SET));
        return false;
    }
}
