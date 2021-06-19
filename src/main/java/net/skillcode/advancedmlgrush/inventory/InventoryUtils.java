package net.skillcode.advancedmlgrush.inventory;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.annotations.PostConstruct;
import net.skillcode.advancedmlgrush.config.configs.ItemNameConfig;
import net.skillcode.advancedmlgrush.config.configs.MainConfig;
import net.skillcode.advancedmlgrush.item.EnumItem;
import net.skillcode.advancedmlgrush.item.GlassColor;
import net.skillcode.advancedmlgrush.item.builder.IBFactory;
import net.skillcode.advancedmlgrush.item.builder.MetaType;
import net.skillcode.advancedmlgrush.item.parser.GlassColorParser;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Singleton
public class InventoryUtils {

    @Inject
    private InventoryManager inventoryManager;
    @Inject
    private MainConfig mainConfig;
    @Inject
    private GlassColorParser glassColorParser;
    @Inject
    private ItemNameConfig itemNameConfig;
    @Inject
    private IBFactory ibFactory;

    private ItemStack background;

    @PostConstruct
    public void init() {
        final GlassColor glassColor = glassColorParser.parse(mainConfig.getString(MainConfig.INVENTORY_BACKGROUND_COLOR));
        background = ibFactory.create(MetaType.ITEM_META, glassColor.getData()).name(itemNameConfig.getString(
                Optional.empty(), EnumItem.INVENTORY_BACKGROUND)).material(Material.STAINED_GLASS_PANE).build();
    }


    public boolean isOpenInventory(final @NotNull Player player, final @NotNull Class<? extends AbstractInventory> clazz) {
        final Optional<Class<? extends AbstractInventory>> optional = inventoryManager.getOpenInventory(player);
        return optional.isPresent() && optional.get().equals(clazz);
    }

    public void frame(final @NotNull Inventory inventory) {
        final int size = inventory.getSize();
        for (int i = 1; i < size + 1; i++) {
            if (i <= 9
                    || i > size - 9
                    || i % 9 <= 1) {
                inventory.setItem(i - 1, background);
            }
        }
    }

    public void fill(final @NotNull Inventory inventory) {
        final int size = inventory.getSize();
        for (int i = 0; i < size; i++) {
            inventory.setItem(i, background);
        }
    }

    public List<Integer> getEmptySlots(final @NotNull Inventory inventory) {
        final List<Integer> slots = new ArrayList<>();
        for (int i = 0; i < inventory.getSize(); i++) {
            final ItemStack itemStack = inventory.getItem(i);
            if (itemStack == null
                    || itemStack.getType() == null
                    || itemStack.getType() == Material.AIR) {
                slots.add(i);
            }
        }
        return slots;
    }

}
