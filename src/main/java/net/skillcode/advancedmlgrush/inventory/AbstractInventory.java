package net.skillcode.advancedmlgrush.inventory;

import com.google.inject.Inject;
import net.skillcode.advancedmlgrush.annotations.PostConstruct;
import net.skillcode.advancedmlgrush.config.configs.SoundConfig;
import net.skillcode.advancedmlgrush.event.EventHandler;
import net.skillcode.advancedmlgrush.event.EventListener;
import net.skillcode.advancedmlgrush.event.EventManager;
import net.skillcode.advancedmlgrush.item.ItemUtils;
import net.skillcode.advancedmlgrush.sound.SoundUtil;
import net.skillcode.advancedmlgrush.util.Pair;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractInventory implements EventHandler {

    private final Inventory inventory = onCreate().getKey();

    @Inject
    protected InventoryManager inventoryManager;
    @Inject
    protected InventoryUtils inventoryUtils;
    @Inject
    protected ItemUtils itemUtils;
    @Inject
    private SoundUtil soundUtil;
    @Inject
    private EventManager eventManager;

    @PostConstruct
    public void init() {
        eventManager.registerEventListeners(this);
    }

    public void open(final @NotNull Player player) {
        Inventory inv = cloneOnOpen() ?
                Bukkit.createInventory(inventory.getHolder(), inventory.getSize(), onCreate().getValue()) :
                inventory;

        inv = onOpen(inv, player);
        player.openInventory(inv);
        inventoryManager.register(player, getClass());

        if (playSound()) {
            soundUtil.playSound(player, SoundConfig.OPEN_INVENTORY);
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
        System.out.println("*1");
        eventListeners.addAll(listeners(new ArrayList<>()));
    }
}
