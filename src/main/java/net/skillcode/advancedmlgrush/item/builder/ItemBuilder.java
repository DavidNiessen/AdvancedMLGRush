package net.skillcode.advancedmlgrush.item.builder;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ItemBuilder {

    private final MetaType metaType;
    private final int data;
    private final ItemStack itemStack;

    @Getter
    private ItemMeta itemMeta;

    protected ItemBuilder(final @NotNull MetaType metaType, final int data) {
        this.metaType = metaType;
        this.data = data;
        this.itemStack = initItemStack();
        this.itemMeta = itemStack.getItemMeta();
    }

    public static ItemBuilder create(final @NotNull MetaType metaType, final int data) {
        return new ItemBuilder(metaType, data);
    }

    @NotNull
    private ItemStack initItemStack() {
        final ItemStack itemStack;
        if (metaType == MetaType.SKULL_META) {
            itemStack = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
        } else {
            itemStack = new ItemStack(Material.STONE, 1, Byte.parseByte(String.valueOf(data)));
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
        itemMeta.spigot().setUnbreakable(true);
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
