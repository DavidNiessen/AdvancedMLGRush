package net.skillcode.advancedmlgrush.inventory.multipage;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.List;

public interface MPHFactory {

    MultiPageHelper create(final @NotNull Inventory inventory,
                              final @NotNull List<Integer> elementSlots,
                              final @NotNull LinkedHashMap<ItemStack, Object> elements);

}
