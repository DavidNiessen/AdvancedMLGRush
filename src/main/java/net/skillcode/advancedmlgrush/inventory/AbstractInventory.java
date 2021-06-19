package net.skillcode.advancedmlgrush.inventory;

import com.google.inject.Inject;
import net.skillcode.advancedmlgrush.config.configs.SoundConfig;
import net.skillcode.advancedmlgrush.event.EventHandler;
import net.skillcode.advancedmlgrush.event.EventListener;
import net.skillcode.advancedmlgrush.event.EventManager;
import net.skillcode.advancedmlgrush.item.ItemManager;
import net.skillcode.advancedmlgrush.item.ItemUtils;
import net.skillcode.advancedmlgrush.item.builder.IBFactory;
import net.skillcode.advancedmlgrush.sound.SoundUtil;
import net.skillcode.advancedmlgrush.util.Pair;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractInventory implements EventHandler {

    @Inject
    protected InventoryManager inventoryManager;
    @Inject
    protected InventoryUtils inventoryUtils;
    @Inject
    protected ItemUtils itemUtils;
    @Inject
    protected ItemManager itemManager;
    @Inject
    protected IBFactory ibFactory;
    @Inject
    protected SoundUtil soundUtil;
    @Inject
    private EventManager eventManager;

    private Inventory baseInventory;
    private Pair<Inventory, String> inventoryPair;

    public void init() {
        eventManager.registerEventListeners(this);
        inventoryPair = onCreate();
        baseInventory = inventoryPair.getKey();
    }

    public void open(final @NotNull Player player) {
        Inventory inventory;
        if (cloneOnOpen()) {
            inventory = Bukkit.createInventory(baseInventory.getHolder(), baseInventory.getSize(), inventoryPair.getValue());
            inventory.setContents(baseInventory.getContents());
        } else {
            inventory = baseInventory;
        }

        inventory = onOpen(inventory, player);
        player.openInventory(inventory);
        inventoryManager.register(player, this.getClass());

        if (playSound()) {
            soundUtil.playSound(player, SoundConfig.INVENTORY_OPEN);
        }
    }

    protected abstract boolean cloneOnOpen();

    protected abstract boolean playSound();

    //<inventory, title>
    protected abstract Pair<Inventory, String> onCreate();

    protected abstract Inventory onOpen(final @NotNull Inventory inventory, final @NotNull Player player);

    protected abstract List<EventListener<?>> listeners(final @NotNull List<EventListener<?>> eventListeners);

    @Override
    public void registerListeners(final @NotNull List<EventListener<?>> eventListeners) {
        eventListeners.addAll(listeners(new ArrayList<>()));
    }
}
