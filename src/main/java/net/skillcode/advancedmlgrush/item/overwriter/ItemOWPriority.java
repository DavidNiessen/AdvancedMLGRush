/*
 * Copyright (c) 2021 SkillCode
 *
 * This file is a part of the source code of the
 * AdvancedMLGRush plugin by SkillCode.
 *
 * This file may only be used in compliance with the
 * LICENSE.txt (https://github.com/SkillC0de/AdvancedMLGRush/blob/master/LICENSE.txt).
 *
 * Support: https://discord.skillplugins.com
 */

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
