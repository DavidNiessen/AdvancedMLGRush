package net.skillcode.advancedmlgrush.item.items.handlers;

import com.google.inject.Inject;
import net.skillcode.advancedmlgrush.event.EventHandler;
import net.skillcode.advancedmlgrush.event.EventListener;
import net.skillcode.advancedmlgrush.item.EnumItem;
import net.skillcode.advancedmlgrush.item.ItemManager;
import net.skillcode.advancedmlgrush.item.ItemUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class ChallengerHandler implements EventHandler {

    @Inject
    private ItemUtils itemUtils;
    @Inject
    private ItemManager itemManager;

    @Override
    public void registerListeners(final @NotNull List<EventListener<?>> eventListeners) {
        eventListeners.add(onRightClick());
    }

    private EventListener<PlayerInteractEvent> onRightClick() {
        return new EventListener<PlayerInteractEvent>(PlayerInteractEvent.class) {
            @Override
            protected void onEvent(final @NotNull PlayerInteractEvent event) {
                final Player player = event.getPlayer();
                final ItemStack clickedItem = player.getItemInHand();

                if (itemUtils.compare(clickedItem, EnumItem.CHALLENGER, Optional.of(player))) {

                    player.sendMessage("test");
                }
            }
        };
    }
}
