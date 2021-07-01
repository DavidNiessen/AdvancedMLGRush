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

package net.skillcode.advancedmlgrush.game.queue;

import com.google.inject.Inject;
import net.skillcode.advancedmlgrush.config.configs.MainConfig;
import net.skillcode.advancedmlgrush.config.configs.MessageConfig;
import net.skillcode.advancedmlgrush.config.configs.SoundConfig;
import net.skillcode.advancedmlgrush.game.map.MapManager;
import net.skillcode.advancedmlgrush.game.map.MapTemplate;
import net.skillcode.advancedmlgrush.game.map.MapType;
import net.skillcode.advancedmlgrush.game.scoreboard.ScoreboardManager;
import net.skillcode.advancedmlgrush.item.items.LobbyItems;
import net.skillcode.advancedmlgrush.miscellaneous.registrable.Registrable;
import net.skillcode.advancedmlgrush.sound.SoundUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class Queue implements Registrable {

    private final List<Player> queue = new CopyOnWriteArrayList<>();
    @Inject
    private LobbyItems lobbyItems;
    @Inject
    private MessageConfig messageConfig;
    @Inject
    private SoundUtil soundUtil;
    @Inject
    private MainConfig mainConfig;
    @Inject
    private MapManager mapManager;
    @Inject
    private JavaPlugin javaPlugin;
    @Inject
    private ScoreboardManager scoreboardManager;

    /**
     * @return the number of players that can play on this map
     */
    public int getSize() {
        return queue.size();
    }


    public void register(final @NotNull Player player) {
        final Optional<Player> optionalPlayer = Optional.of(player);
        scoreboardManager.updateScoreboard();
        if (queue.contains(player)
                || queue.size() >= playerAmount()) {
            lobbyItems.setLobbyItems(player);
            player.sendMessage(messageConfig.getWithPrefix(optionalPlayer, MessageConfig.ERROR));
        } else {
            player.getInventory().clear();
            lobbyItems.setQueueItems(player);
            queue.add(player);
            player.sendMessage(messageConfig.getWithPrefix(optionalPlayer, MessageConfig.QUEUE_JOIN));

            if (queue.size() == playerAmount()) {
                queue.forEach(player1 -> player1.getInventory().clear());
                startGame();
                queue.clear();
            }
        }
    }

    @Override
    public void unregister(final @NotNull Player player) {
        queue.remove(player);
    }

    public void removeFromQueue(final @NotNull Player player) {
        if (queue.contains(player)) {
            soundUtil.playSound(player, SoundConfig.QUEUE_LEAVE);
            queue.remove(player);
            player.getInventory().clear();
            player.sendMessage(messageConfig.getWithPrefix(Optional.of(player), MessageConfig.QUEUE_LEAVE));
            Bukkit.getScheduler().scheduleSyncDelayedTask(javaPlugin, () -> lobbyItems.setLobbyItems(player), 5);
        }
    }

    protected abstract int playerAmount();

    abstract MapType mapType();

    private void startGame() {
        final Optional<MapTemplate> mapTemplate = mapManager.getRandomMapTemplate(mapType());
        if (!mapTemplate.isPresent()) {
            queue.forEach(player -> {
                player.sendMessage(messageConfig.getWithPrefix(Optional.of(player), MessageConfig.ERROR));
                lobbyItems.setLobbyItems(player);
            });
        } else {
            final int rounds = mainConfig.getInt(MainConfig.DEFAULT_ROUNDS);
            mapTemplate.get().createInstance(new ArrayList<>(queue), rounds);
        }
    }
}
