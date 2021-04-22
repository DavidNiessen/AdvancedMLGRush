package net.skillcode.advancedmlgrush.item.rule;

import net.skillcode.advancedmlgrush.item.EnumItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

/**
 * Used to override items from configs
 * <p>
 * @see ItemRuleManager
 */
public interface ItemRule {

    EnumItem getEnumItem();

    ItemRulePriority getPriority();

    ItemStack getItemStack(final Optional<Player> optionalPlayer);

}
