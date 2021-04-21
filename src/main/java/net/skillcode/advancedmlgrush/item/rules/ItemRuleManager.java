package net.skillcode.advancedmlgrush.item.rules;

import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.item.EnumItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class ItemRuleManager {

    private final Map<EnumItem, ItemRule> rules = new ConcurrentHashMap<>();

    public void registerItemRule(final @NotNull ItemRule itemRule) {

       if (rules.containsKey(itemRule.getEnumItem()) && itemRule.getPriority() != ItemRulePriority.HIGH) {
           return;
       }

       rules.put(itemRule.getEnumItem(), itemRule);
    }

    public Optional<ItemStack> getItem(final @NotNull Optional<Player> optionalPlayer, final @NotNull EnumItem enumItem) {
        if (rules.containsKey(enumItem)) {
            return Optional.of(rules.get(enumItem).getItemStack(optionalPlayer));
        }

        return Optional.empty();
    }

}
