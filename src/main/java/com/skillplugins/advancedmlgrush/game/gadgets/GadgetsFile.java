/*
 * Copyright (c) 2021 SkillCode
 *
 * This file is a part of the source code of the
 * AdvancedMLGRush plugin by SkillCode.
 *
 * This file may only be used in compliance with the
 * LICENSE.txt (https://github.com/SkillC0de/AdvancedMLGRush/blob/master/LICENSE.txt).
 *
 * Support: http://discord.skillplugins.com
 */

package com.skillplugins.advancedmlgrush.game.gadgets;

import com.skillplugins.advancedmlgrush.util.json.JsonConfig;
import lombok.Getter;

import java.util.List;

@Getter
public class GadgetsFile implements JsonConfig {

    private List<Gadget> sticks = DefaultGadgets.getDefaultSticks();
    private List<Gadget> blocks = DefaultGadgets.getDefaultBlocks();

}
