package net.skillcode.advancedmlgrush.inventory.inventories;

import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.event.EventListener;
import net.skillcode.advancedmlgrush.inventory.AbstractInventory;
import net.skillcode.advancedmlgrush.item.builder.ItemBuilder;
import net.skillcode.advancedmlgrush.item.builder.MetaType;
import net.skillcode.advancedmlgrush.util.Pair;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Singleton
public class TestInventory extends AbstractInventory {

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
        return new Pair<>(Bukkit.createInventory(null, 6 * 9, "test"), "test");
    }

    @Override
    protected Inventory onOpen(final @NotNull Inventory inventory, final @NotNull Player player) {
        inventory.addItem(ItemBuilder.create(MetaType.ITEM_META, 0).name("test").material(Material.BEDROCK).build());
        return inventory;
    }

    @Override
    protected List<EventListener<?>> listeners(final @NotNull List<EventListener<?>> eventListeners) {
        eventListeners.add(new EventListener<InventoryClickEvent>(InventoryClickEvent.class) {
            @Override
            protected void onEvent(final @NotNull InventoryClickEvent event) {
                System.out.println(1);
                final Player player = (Player) event.getWhoClicked();
                if (inventoryUtils.isOpenInventory(player, TestInventory.class)) {
                    System.out.println(2);
                    final ItemStack itemStack = event.getCurrentItem();
                    if (itemUtils.isValidItem(itemStack)
                            && itemStack.getItemMeta().getDisplayName().equals("test")) {
                        System.out.println(3);
                        player.sendMessage("teeest");
                    }
                }
            }
        });
        return eventListeners;
    }


}
