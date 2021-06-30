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

package net.skillcode.advancedmlgrush.game.gadgets;

import net.skillcode.advancedmlgrush.libs.xseries.XMaterial;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DefaultGadgets {

    private DefaultGadgets() {
    }

    public static List<Gadget> getDefaultSticks() {
        return new ArrayList<>(Arrays.asList(
                new Gadget(
                        "&8» &eStick",
                        XMaterial.STICK.name(),
                        "none",
                        "&aUnlocked",
                        " "
                ),
                new Gadget(
                        "&8» &eBlaze Rod",
                        XMaterial.BLAZE_ROD.name(),
                        "gadgets.vip",
                        "&aUnlocked",
                        "&cRequires &6VIP"
                ),
                new Gadget(
                        "&8» &eFeather",
                        XMaterial.FEATHER.name(),
                        "gadgets.vip",
                        "&aUnlocked",
                        "&cRequires &6VIP"
                ),
                new Gadget(
                        "&8» &eArrow",
                        XMaterial.ARROW.name(),
                        "gadgets.mvp",
                        "&aUnlocked",
                        "&cRequires &bMVP"
                ),
                new Gadget(
                        "&8» &eShears",
                        XMaterial.SHEARS.name(),
                        "gadgets.mvp",
                        "&aUnlocked",
                        "&cRequires &bMVP"
                )
        ));
    }

    public static List<Gadget> getDefaultBlocks() {
        return new ArrayList<>(Arrays.asList(
                new Gadget(
                        "&8» &eSandstone",
                        XMaterial.SANDSTONE.name(),
                        "none",
                        "&aUnlocked",
                        "&cRequires &6VIP"
                ),
                new Gadget(
                        "&8» &eGold Block",
                        XMaterial.GOLD_BLOCK.name(),
                        "gadgets.vip",
                        "&aUnlocked",
                        "&cRequires &6VIP"
                ),
                new Gadget(
                        "&8» &eGlass",
                        XMaterial.GLASS.name(),
                        "gadgets.vip",
                        "&aUnlocked",
                        "&cRequires &6VIP"
                ),
                new Gadget(
                        "&8» &eCobblestone",
                        XMaterial.COBBLESTONE.name(),
                        "gadgets.mvp",
                        "&aUnlocked",
                        "&cRequires &bMVP"
                ),
                new Gadget(
                        "&8» &eNether Brick",
                        XMaterial.NETHER_BRICKS.name(),
                        "gadgets.mvp",
                        "&aUnlocked",
                        "&cRequires &bMVP"
                )
        ));
    }

}
