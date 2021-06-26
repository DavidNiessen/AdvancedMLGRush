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

package net.skillcode.advancedmlgrush.game.queue;

import com.google.inject.Inject;
import net.skillcode.advancedmlgrush.config.configs.MessageConfig;
import net.skillcode.advancedmlgrush.config.configs.SoundConfig;
import net.skillcode.advancedmlgrush.item.items.LobbyItems;
import net.skillcode.advancedmlgrush.miscellaneous.registrable.Registrable;
import net.skillcode.advancedmlgrush.sound.SoundUtil;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class Queue implements Registrable {

    @Inject
    private LobbyItems lobbyItems;
    @Inject
    private MessageConfig messageConfig;
    @Inject
    private SoundUtil soundUtil;

    private final List<Player> queue = new CopyOnWriteArrayList<>();

    /**
     * @return the number of players that can play on this arena
     */
    protected abstract int playerAmount();

    public int getSize() {
        return queue.size();
    }

    public void register(final @NotNull Player player) {
        final Optional<Player> optionalPlayer = Optional.of(player);
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
                queue.forEach(player1 -> {
                    player.getInventory().clear();
                    // TODO: 25.06.21 start game
                });
            }
        }
    }

    @Override
    public void unregister(final @NotNull Player player) {
        if (queue.contains(player)) {
            soundUtil.playSound(player, SoundConfig.QUEUE_LEAVE);
            queue.remove(player);
            player.getInventory().clear();
            lobbyItems.setLobbyItems(player);
            player.sendMessage(messageConfig.getWithPrefix(Optional.of(player), MessageConfig.QUEUE_LEAVE));
        }
    }
}
