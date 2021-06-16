package net.skillcode.advancedmlgrush.item;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.annotations.PostConstruct;
import net.skillcode.advancedmlgrush.config.configs.ItemNameConfig;
import net.skillcode.advancedmlgrush.config.configs.MaterialConfig;
import net.skillcode.advancedmlgrush.item.builder.IBFactory;
import net.skillcode.advancedmlgrush.item.builder.ItemBuilder;
import net.skillcode.advancedmlgrush.item.builder.MetaType;
import net.skillcode.advancedmlgrush.item.rule.ItemRuleManager;
import net.skillcode.advancedmlgrush.item.rule.RuleRegistry;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

@Singleton
public class ItemManager {

    @Inject
    private ItemNameConfig itemNameConfig;
    @Inject
    private MaterialConfig materialConfig;
    @Inject
    private ItemRuleManager itemRuleManager;
    @Inject
    private RuleRegistry ruleRegistrator;
    @Inject
    private IBFactory ibFactory;

    @PostConstruct
    public void init() {
        ruleRegistrator.registerRules();
    }

    public ItemStack getItem(final @NotNull Optional<Player> optionalPlayer, final @NotNull EnumItem enumItem) {

        final Optional<ItemStack> optional = itemRuleManager.getItem(optionalPlayer, enumItem);

        return optional.orElseGet(() -> ibFactory.create(MetaType.ITEM_META, getConfigData(enumItem))
                .name(itemNameConfig.getString(optionalPlayer, enumItem.getConfigPath()))
                .material(getConfigMaterial(enumItem))
                .unbreakable()
                .hideAttributes()
                .hideEnchants()
                .hideUnbreakable()
                .build());
    }

    private Material getConfigMaterial(final @NotNull EnumItem enumItem) {
        return materialConfig.getMaterial(enumItem);
    }

    private int getConfigData(final @NotNull EnumItem enumItem) {
        return materialConfig.getData(enumItem);
    }

}
