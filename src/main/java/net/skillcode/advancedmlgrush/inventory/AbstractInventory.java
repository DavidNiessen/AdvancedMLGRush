package net.skillcode.advancedmlgrush.inventory;

import com.google.inject.Inject;
import net.skillcode.advancedmlgrush.config.configs.SoundConfig;
import net.skillcode.advancedmlgrush.event.EventHandler;
import net.skillcode.advancedmlgrush.event.EventListener;
import net.skillcode.advancedmlgrush.sound.SoundUtil;
import net.skillcode.advancedmlgrush.util.Pair;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class AbstractInventory implements EventHandler {

    private final Inventory inventory = onCreate().getKey();

    @Inject
    protected InventoryManager inventoryManager;
    @Inject
    private SoundUtil soundUtil;

    public void open(final @NotNull Player player) {
        player.openInventory(cloneOnOpen() ?
                Bukkit.createInventory(inventory.getHolder(), inventory.getSize(), onCreate().getValue()) :
                inventory);

        if (playSound()) {
            soundUtil.playSound(player, SoundConfig.OPEN_INVENTORY);
        }
    }

    protected abstract boolean cloneOnOpen();

    protected abstract boolean playSound();

    protected abstract Pair<Inventory, String> onCreate();

    protected abstract Inventory onOpen(final @NotNull Inventory inventory, final @NotNull Player player);

    @Override
    public void registerListeners(final @NotNull List<EventListener<?>> eventListeners) {
        final Class<? extends AbstractInventory> clazz = getClass();
        eventListeners.add(new EventListener<PlayerJoinEvent>(InventoryOpenEvent.class) {

            @Override
            protected void onEvent(final @NotNull PlayerJoinEvent event) {
                inventoryManager.register(event.getPlayer(), clazz);
            }
        });
    }
}
