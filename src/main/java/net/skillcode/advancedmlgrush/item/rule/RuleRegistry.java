package net.skillcode.advancedmlgrush.item.rule;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.item.rule.rules.StatsItemRule;

@Singleton
public class RuleRegistry {

    @Inject
    private ItemRuleManager itemRuleManager;
    @Inject
    private StatsItemRule statsItemRule;

    public void registerRules() {
        itemRuleManager.registerItemRule(statsItemRule);
    }
}
