package net.skillcode.advancedmlgrush.item.overwriter;

/**
 * This enum defines the importance of an item overwriter
 * <p>
 * LOW: if you want to overwrite an item
 * HIGH: If you want to overwrite another overwriter
 * <p>
 * @see ItemOW
 * @see ItemOWManager
 */
public enum ItemOWPriority {
    LOW,
    HIGH;
}
