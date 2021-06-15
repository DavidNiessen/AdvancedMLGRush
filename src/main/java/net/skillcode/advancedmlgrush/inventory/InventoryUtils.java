package net.skillcode.advancedmlgrush.inventory;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

@Singleton
public class InventoryUtils {

    private final InventoryManager inventoryManager;

    @Inject
    public InventoryUtils(final @NotNull InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
    }

    public boolean isOpenInventory(final @NotNull Player player, final @NotNull  Class<? extends AbstractInventory> clazz) {
        final Optional<Class<? extends AbstractInventory>> optional = inventoryManager.getOpenInventory(player);

        return optional.isPresent() && optional.get().equals(clazz);
    }

    public void frame(final @NotNull Inventory inventory, final @NotNull ItemStack itemStack) {
        // TODO: 15.06.2021
    }

    public void fill(final @NotNull Inventory inventory, final @NotNull ItemStack itemStack) {
        // TODO: 15.06.2021
    }

}
