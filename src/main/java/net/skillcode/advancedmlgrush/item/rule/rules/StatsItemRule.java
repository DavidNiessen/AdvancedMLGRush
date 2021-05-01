package net.skillcode.advancedmlgrush.item.rule.rules;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.config.configs.ItemNameConfig;
import net.skillcode.advancedmlgrush.item.EnumItem;
import net.skillcode.advancedmlgrush.item.builder.ItemBuilder;
import net.skillcode.advancedmlgrush.item.builder.MetaType;
import net.skillcode.advancedmlgrush.item.rule.ItemRule;
import net.skillcode.advancedmlgrush.item.rule.ItemRulePriority;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

@Singleton
public class StatsItemRule implements ItemRule {

    private final ItemNameConfig itemNameConfig;

    @Inject
    public StatsItemRule(final ItemNameConfig itemNameConfig) {
        this.itemNameConfig = itemNameConfig;
    }

    @Override
    public EnumItem getEnumItem() {
        return EnumItem.STATS;
    }

    @Override
    public ItemRulePriority getPriority() {
        return ItemRulePriority.LOW;
    }

    @Override
    public ItemStack getItemStack(final @NotNull Optional<Player> optionalPlayer) {
        final String itemName = itemNameConfig.getString(optionalPlayer, EnumItem.STATS);

        return optionalPlayer.map(player -> ItemBuilder.create(MetaType.SKULL_META, 3)
                .owner(player.getName()).name(itemName).build()).orElseGet(()
                -> ItemBuilder.create(MetaType.SKULL_META, 3).name(itemName).build());

    }
}
