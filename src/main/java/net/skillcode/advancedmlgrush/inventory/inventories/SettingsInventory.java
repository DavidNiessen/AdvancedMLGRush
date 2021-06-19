package net.skillcode.advancedmlgrush.inventory.inventories;

import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.annotations.PostConstruct;
import net.skillcode.advancedmlgrush.config.configs.InventoryNameConfig;
import net.skillcode.advancedmlgrush.event.EventListener;
import net.skillcode.advancedmlgrush.inventory.AbstractInventory;
import net.skillcode.advancedmlgrush.item.EnumItem;
import net.skillcode.advancedmlgrush.util.Pair;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

@Singleton
public class SettingsInventory extends AbstractInventory {

    @PostConstruct
    public void initInventory() {
        super.init();
    }

    @Override
    protected boolean cloneOnOpen() {
        return false;
    }

    @Override
    protected boolean playSound() {
        return true;
    }

    @Override
    protected Pair<Inventory, String> onCreate() {
        final String title = inventoryUtils.getInventoryName(InventoryNameConfig.SETTINGS);
        final Inventory inventory = Bukkit.createInventory(null, 3 * 9, title);
        inventoryUtils.fill(inventory);

        inventory.setItem(10, itemManager.getItem(Optional.empty(), EnumItem.SETTINGS_INVENTORY_SORTING));
        inventory.setItem(12, itemManager.getItem(Optional.empty(), EnumItem.SETTINGS_MAP));
        inventory.setItem(14, itemManager.getItem(Optional.empty(), EnumItem.SETTINGS_STICK));
        inventory.setItem(16, itemManager.getItem(Optional.empty(), EnumItem.SETTINGS_BLOCKS));

        return new Pair<>(inventory, title);
    }

    @Override
    protected Inventory onOpen(final @NotNull Inventory inventory, final @NotNull Player player) {
        return inventory;
    }

    @Override
    protected List<EventListener<?>> listeners(final @NotNull List<EventListener<?>> eventListeners) {
        return eventListeners;
    }
}
