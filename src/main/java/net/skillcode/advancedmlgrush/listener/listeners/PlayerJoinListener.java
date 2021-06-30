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

package net.skillcode.advancedmlgrush.listener.listeners;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.event.EventManager;
import net.skillcode.advancedmlgrush.game.spawn.SpawnFile;
import net.skillcode.advancedmlgrush.game.spawn.SpawnFileLoader;
import net.skillcode.advancedmlgrush.item.items.LobbyItems;
import net.skillcode.advancedmlgrush.util.json.JsonLocation;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

@Singleton
public class PlayerJoinListener implements Listener {

    @Inject
    private LobbyItems lobbyItems;
    @Inject
    private EventManager eventManager;
    @Inject
    private SpawnFileLoader spawnFileLoader;

    @EventHandler
    public void onJoin(final @NotNull PlayerJoinEvent event) {
        event.setJoinMessage(" ");
        eventManager.callEvent(event);

        final Player player = event.getPlayer();

        final Optional<SpawnFile> spawnFileOptional = spawnFileLoader.getSpawnFileOptional();
        if (spawnFileOptional.isPresent()) {
            final JsonLocation jsonLocation = spawnFileOptional.get().getJsonLocation();
            player.teleport(new Location(Bukkit.getWorld(jsonLocation.getWorldName()), jsonLocation.getX(),
                    jsonLocation.getY(), jsonLocation.getZ(), jsonLocation.getPitch(), jsonLocation.getYaw()));
        }

        player.getInventory().clear();
        player.setHealth(20D);
        player.setFoodLevel(20);
        player.setGameMode(GameMode.SURVIVAL);
        player.getActivePotionEffects().clear();
        player.setFlying(false);
        player.setAllowFlight(false);

        lobbyItems.setLobbyItems(event.getPlayer());
    }

}
