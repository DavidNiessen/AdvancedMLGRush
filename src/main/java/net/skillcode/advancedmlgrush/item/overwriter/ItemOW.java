package net.skillcode.advancedmlgrush.item.overwriter;

import net.skillcode.advancedmlgrush.item.EnumItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

/**
 * Used to override items from configs
 * <p>
 * @see ItemOWManager
 */
public interface ItemOW {

    EnumItem getEnumItem();

    ItemOWPriority getPriority();

    ItemStack getItemStack(final Optional<Player> optionalPlayer);

}
