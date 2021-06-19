package net.skillcode.advancedmlgrush.item.items.handlers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.event.EventHandler;
import net.skillcode.advancedmlgrush.event.EventListener;
import net.skillcode.advancedmlgrush.inventory.inventories.SettingsInventory;
import net.skillcode.advancedmlgrush.item.EnumItem;
import net.skillcode.advancedmlgrush.item.ItemUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

@Singleton
public class SettingsHandler implements EventHandler {

    @Inject
    private ItemUtils itemUtils;
    @Inject
    private SettingsInventory settingsInventory;

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

                    if (itemUtils.compare(clickedItem, EnumItem.SETTINGS, Optional.of(player))) {
                        settingsInventory.open(player);
                    }
                }
            }
        };
    }

}
