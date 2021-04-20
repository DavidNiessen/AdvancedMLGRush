package net.skillcode.advancedmlgrush.item;

import com.google.inject.Singleton;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

@Singleton
public class ItemUtils {

    public boolean isValidItem(final @Nullable ItemStack itemStack) {
        return isValidType(itemStack) && isValidName(itemStack);
    }

    public boolean isValidType(final @Nullable ItemStack itemStack) {
        return itemStack != null
                && itemStack.getType() != null
                && itemStack.getType() != Material.AIR;
    }

    public boolean isValidName(final @Nullable ItemStack itemStack) {
        return itemStack != null
                && itemStack.getItemMeta() != null
                && itemStack.getItemMeta().getDisplayName() != null;
    }

}
