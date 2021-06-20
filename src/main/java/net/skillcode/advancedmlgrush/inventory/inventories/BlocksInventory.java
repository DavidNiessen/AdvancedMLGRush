package net.skillcode.advancedmlgrush.inventory.inventories;

import com.google.inject.Inject;
import net.skillcode.advancedmlgrush.config.configs.InventoryNameConfig;
import net.skillcode.advancedmlgrush.config.configs.SoundConfig;
import net.skillcode.advancedmlgrush.game.gadgets.Gadget;
import net.skillcode.advancedmlgrush.game.gadgets.GadgetManager;
import net.skillcode.advancedmlgrush.inventory.multipage.MultiPageInventory;
import net.skillcode.advancedmlgrush.item.builder.ItemBuilder;
import net.skillcode.advancedmlgrush.sql.data.CachedSQLData;
import net.skillcode.advancedmlgrush.sql.data.SQLDataCache;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Optional;

public class BlocksInventory extends MultiPageInventory {

    @Inject
    private GadgetManager gadgetManager;
    @Inject
    private SQLDataCache sqlDataCache;

    @Override
    protected boolean playSound() {
        return false;
    }

    @Override
    protected String title() {
        return inventoryUtils.getInventoryName(InventoryNameConfig.BLOCKS);
    }

    @Override
    protected LinkedHashMap<ItemStack, Object> onOpen(final @NotNull Player player) {
        final LinkedHashMap<ItemStack, Object> map = new LinkedHashMap<>();

        for (int i = 0; i < gadgetManager.getBlocks().size(); i++) {
            final Gadget gadget = gadgetManager.getBlocks().get(i);
            final ItemBuilder itemBuilder = gadgetManager.getGadgetAsBuilder(player, gadget);

            if (gadgetManager.getBlock(player).equals(gadget)) {
                itemBuilder.enchantment(Enchantment.LUCK, 1);
            }

            map.put(itemBuilder.build(), gadget);
        }
        return map;
    }

    @Override
    protected void onElementClick(final Player player, final @NotNull Optional<Object> optional) {
        if (optional.isPresent() && optional.get() instanceof Gadget) {
            final Gadget gadget = (Gadget) optional.get();

            if (player.hasPermission(gadget.getPermission())) {
                final CachedSQLData cachedSQLData = sqlDataCache.getSQLData(player);
                cachedSQLData.setGadgetsBlocks(gadgetManager.getBlocks().indexOf(gadget));
                soundUtil.playSound(player, SoundConfig.INVENTORY_CLICK);
            } else {
                soundUtil.playSound(player, SoundConfig.INVENTORY_CLICK_LOCKED);
            }
        }
    }

}
