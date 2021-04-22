package net.skillcode.advancedmlgrush.item.rule;

/**
 * This enum defines the importance of a rule
 * <p>
 * LOW: if you want to overwrite an item
 * HIGH: If you want to overwrite another rule
 * <p>
 * @see ItemRule
 * @see ItemRuleManager
 */
public enum ItemRulePriority {
    LOW,
    HIGH;
}
