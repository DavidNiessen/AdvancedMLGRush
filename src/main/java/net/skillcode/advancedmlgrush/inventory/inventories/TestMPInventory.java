package net.skillcode.advancedmlgrush.inventory.inventories;

import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.inventory.multipage.MultiPageInventory;
import net.skillcode.advancedmlgrush.item.builder.MetaType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Optional;

@Singleton
public class TestMPInventory extends MultiPageInventory {

    @Override
    protected boolean playSound() {
        return true;
    }

    @Override
    protected String title() {
        return "test";
    }

    @Override
    protected LinkedHashMap<ItemStack, Object> onOpen(final @NotNull Player player) {
        final LinkedHashMap<ItemStack, Object> map = new LinkedHashMap<>();
        for (int i = 0; i < 100; i++) {
            map.put(ibFactory.create(MetaType.ITEM_META, 0).material(Material.STONE).name("" + i).build(), i);
        }
        return map;
    }

    @Override
    protected void onElementClick(final @NotNull Player player, final @NotNull Optional<Object> optional) {
        player.sendMessage(optional.isPresent() ? "Y" + optional.get() : "N");
    }
}
