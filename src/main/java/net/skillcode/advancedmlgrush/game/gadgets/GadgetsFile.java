/*
 * Copyright (c) 2021 SkillCode
 *
 * This file is a part of the source code of the
 * AdvancedMLGRush plugin by SkillCode.
 *
 * This class may only be used in compliance with the
 * LICENSE.txt (https://github.com/SkillC0de/AdvancedMLGRush/blob/master/LICENSE.txt).
 *
 * Support: https://discord.skillplugins.com
 */

package net.skillcode.advancedmlgrush.game.gadgets;

import lombok.Getter;
import net.skillcode.advancedmlgrush.util.json.JsonConfig;

import java.util.List;

@Getter
public class GadgetsFile implements JsonConfig {

    private List<Gadget> sticks = DefaultGadgets.getDefaultSticks();
    private List<Gadget> blocks = DefaultGadgets.getDefaultBlocks();

}
