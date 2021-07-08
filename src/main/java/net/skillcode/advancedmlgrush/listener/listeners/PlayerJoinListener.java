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

package net.skillcode.advancedmlgrush.listener.listeners;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.event.EventManager;
import net.skillcode.advancedmlgrush.game.scoreboard.ScoreboardManager;
import net.skillcode.advancedmlgrush.game.spawn.SpawnFile;
import net.skillcode.advancedmlgrush.game.spawn.SpawnFileLoader;
import net.skillcode.advancedmlgrush.item.items.LobbyItems;
import net.skillcode.advancedmlgrush.util.LocationWrapper;
import org.bukkit.GameMode;
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
    @Inject
    private ScoreboardManager scoreboardManager;
    @Inject
    private LocationWrapper locationWrapper;

    @EventHandler
    public void onJoin(final @NotNull PlayerJoinEvent event) {
        event.setJoinMessage("");
        eventManager.callEvent(event);

        final Player player = event.getPlayer();

        final Optional<SpawnFile> spawnFileOptional = spawnFileLoader.getSpawnFileOptional();
        spawnFileOptional.ifPresent(spawnFile -> player.teleport(locationWrapper.toLocation(spawnFile.getJsonLocation())));

        player.getInventory().clear();
        player.setHealth(20D);
        player.setFoodLevel(20);
        player.setLevel(0);
        player.setGameMode(GameMode.SURVIVAL);
        player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));
        player.setFlying(false);
        player.setAllowFlight(false);

        lobbyItems.setLobbyItems(event.getPlayer());
        scoreboardManager.updateScoreboard(player);
    }

}
