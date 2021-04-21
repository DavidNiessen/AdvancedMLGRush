package net.skillcode.advancedmlgrush.item;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.config.configs.ItemNameConfig;
import net.skillcode.advancedmlgrush.config.configs.MaterialConfig;
import net.skillcode.advancedmlgrush.item.builder.ItemBuilder;
import net.skillcode.advancedmlgrush.item.builder.MetaType;
import net.skillcode.advancedmlgrush.item.rules.ItemRuleManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

@Singleton
public class ItemManager {

    private final ItemNameConfig itemNameConfig;
    private final MaterialConfig materialConfig;
    private final ItemRuleManager itemRuleManager;


    @Inject
    public ItemManager(final ItemNameConfig itemNameConfig, final MaterialConfig materialConfig, final ItemRuleManager itemRuleManager) {
        this.itemNameConfig = itemNameConfig;
        this.materialConfig = materialConfig;
        this.itemRuleManager = itemRuleManager;
    }


    public ItemStack getItem(final @NotNull Player player, final @NotNull EnumItem enumItem) {

        final Optional<ItemStack> optional = itemRuleManager.getItem(Optional.of(player), enumItem);

        return optional.orElseGet(() -> ItemBuilder.create(MetaType.ITEM_META, getConfigData(enumItem))
                .name(itemNameConfig.getString(Optional.of(player), enumItem.getConfigPath()))
                .material(getConfigMaterial(enumItem))
                .unbreakable()
                .hideAttributes()
                .hideEnchants()
                .hideUnbreakable()
                .build());

    }

    public Material getConfigMaterial(final @NotNull EnumItem enumItem) {
        return materialConfig.getMaterial(enumItem);
    }

    public int getConfigData(final @NotNull EnumItem enumItem) {
        return materialConfig.getData(enumItem);
    }

}
