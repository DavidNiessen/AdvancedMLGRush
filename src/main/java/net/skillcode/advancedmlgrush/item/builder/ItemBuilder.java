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

package net.skillcode.advancedmlgrush.item.builder;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import lombok.Getter;
import net.skillcode.advancedmlgrush.libs.xseries.XMaterial;
import net.skillcode.advancedmlgrush.util.NMSUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ItemBuilder {

    private final NMSUtils nmsUtils;
    private final MetaType metaType;
    private final int data;
    private final ItemStack itemStack;

    @Getter
    private ItemMeta itemMeta;

    /**
     * @see net.skillcode.advancedmlgrush.item.builder.IBFactory
     */
    @Inject
    public ItemBuilder(final @NotNull NMSUtils nmsUtils,
                       final @Assisted @NotNull MetaType metaType,
                       final @Assisted int data) {
        this.nmsUtils = nmsUtils;
        this.metaType = metaType;
        this.data = data;
        this.itemStack = initItemStack();
        this.itemMeta = itemStack.getItemMeta();
    }

    @NotNull
    private ItemStack initItemStack() {
        final ItemStack itemStack;
        if (metaType == MetaType.SKULL_META) {
            itemStack = new ItemStack(XMaterial.PLAYER_HEAD.parseMaterial(), 1, (byte) 3);
        } else {
            itemStack = new ItemStack(XMaterial.STONE.parseMaterial(), 1, Byte.parseByte(String.valueOf(data)));
        }
        return itemStack;
    }

    @NotNull
    public ItemBuilder material(final @NotNull Material material) {
        itemStack.setType(material);
        return this;
    }

    @NotNull
    public ItemBuilder meta(final @NotNull ItemMeta itemMeta) {
        this.itemMeta = itemMeta;
        return this;
    }

    @NotNull
    public ItemBuilder name(final @NotNull String name) {
        itemMeta.setDisplayName(name);
        return this;
    }

    @NotNull
    public ItemBuilder unbreakable() {
        nmsUtils.setUnbreakable(itemStack);
        return this;
    }

    @NotNull
    public ItemBuilder owner(final @NotNull String owner) {
        if (itemMeta instanceof SkullMeta) {
            ((SkullMeta) itemMeta).setOwner(owner);
        }
        return this;
    }

    @NotNull
    public ItemBuilder amount(final @NotNull Integer amount) {
        itemStack.setAmount(amount);
        return this;
    }

    @NotNull
    public ItemBuilder lore(final @NotNull List<String> list) {
        itemMeta.setLore(list);
        return this;
    }

    @NotNull
    public ItemBuilder durability(final short durability) {
        itemStack.setDurability(durability);
        return this;
    }

    @NotNull
    public ItemBuilder flags(final @NotNull ItemFlag... itemFlags) {
        itemMeta.addItemFlags(itemFlags);
        return this;
    }

    @NotNull
    public ItemBuilder enchantment(final @NotNull Enchantment enchantment, final int level) {
        itemMeta.addEnchant(enchantment, level, true);
        return this;
    }

    @NotNull
    public ItemBuilder hideEnchants() {
        return flags(ItemFlag.HIDE_ENCHANTS);
    }

    @NotNull
    public ItemBuilder hideUnbreakable() {
        return flags(ItemFlag.HIDE_UNBREAKABLE);
    }

    @NotNull
    public ItemBuilder hideAttributes() {
        return flags(ItemFlag.HIDE_ATTRIBUTES);
    }

    @NotNull
    public ItemStack build() {
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
