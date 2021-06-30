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

package net.skillcode.advancedmlgrush.item.items.handlers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.config.configs.MessageConfig;
import net.skillcode.advancedmlgrush.event.EventHandler;
import net.skillcode.advancedmlgrush.event.EventListener;
import net.skillcode.advancedmlgrush.game.queue.Queue1x1;
import net.skillcode.advancedmlgrush.game.queue.Queue1x4;
import net.skillcode.advancedmlgrush.item.EnumItem;
import net.skillcode.advancedmlgrush.item.ItemUtils;
import net.skillcode.advancedmlgrush.item.items.LobbyItems;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

@Singleton
public class QueueLeaveHandler implements EventHandler {

    @Inject
    private ItemUtils itemUtils;
    @Inject
    private LobbyItems lobbyItems;
    @Inject
    private MessageConfig messageConfig;
    @Inject
    private Queue1x1 queue1x1;
    @Inject
    private Queue1x4 queue1x4;

    @Override
    public void registerListeners(final @NotNull List<EventListener<?>> eventListeners) {
        eventListeners.add(onRightClick());
    }

    private EventListener<PlayerInteractEvent> onRightClick() {
        return new EventListener<PlayerInteractEvent>(PlayerInteractEvent.class) {
            @Override
            protected void onEvent(final @NotNull PlayerInteractEvent event) {
                if (event.getAction() == Action.RIGHT_CLICK_AIR
                        || event.getAction() == Action.RIGHT_CLICK_BLOCK) {

                    final Player player = event.getPlayer();
                    final ItemStack clickedItem = player.getItemInHand();

                    if (itemUtils.compare(clickedItem, EnumItem.QUEUE_LEAVE, Optional.of(player))) {

                        player.getInventory().clear();
                        lobbyItems.setLobbyItems(player);

                        queue1x1.unregister(player);
                        queue1x4.unregister(player);
                    }
                }
            }
        };
    }
}
