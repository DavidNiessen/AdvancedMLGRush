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

package net.skillcode.advancedmlgrush.inventory.multipage;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import net.skillcode.advancedmlgrush.annotations.PostConstruct;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class MultiPageHelper {

    private final Inventory inventory;
    private final List<Integer> elementSlots;
    private final Map<ItemStack, Object> elements;

    private PageUtils<ItemStack> itemPageUtils;
    private PageUtils<Object> elementPageUtils;

    private int currentPage = 0;

    @Inject
    public MultiPageHelper(final @Assisted @NotNull Inventory inventory,
                           final @Assisted @NotNull List<Integer> elementSlots,
                           final @Assisted @NotNull LinkedHashMap<ItemStack, Object> elements) {
        this.inventory = inventory;
        this.elementSlots = elementSlots;
        this.elements = elements;
    }

    @PostConstruct
    public void init() {
        itemPageUtils = new PageUtils<>(elementSlots.size(), new ArrayList<>(elements.keySet()));
        elementPageUtils = new PageUtils<>(elementSlots.size(), new ArrayList<>(elements.values()));
    }

    public boolean loadFirstPage() {
        return loadPage(0);
    }

    public boolean loadNextPage() {
        final boolean bool = loadPage(currentPage + 1);
        if (bool) {
            currentPage++;
        }
        return bool;
    }

    public boolean loadPreviousPage() {
        final boolean bool = currentPage > 0;
        if (bool) {
            loadPage(--currentPage);
        }
        return bool;
    }

    public Optional<Object> getElement(final int clickedSlot) {
        final List<Object> elementList = elementPageUtils.getPage(currentPage);
        if (!elementList.isEmpty()
                && elementList.size() >= elementSlots.indexOf(clickedSlot)) {
            return Optional.of(elementList.get(elementSlots.indexOf(clickedSlot)));
        }
        return Optional.empty();
    }

    private boolean loadPage(final int page) {
        final List<ItemStack> pageItems = getPageItems(page);

        if (pageItems.isEmpty()) {
            return false;
        }
        clearSlots();

        for (int i = 0; i < pageItems.size(); i++) {
            inventory.setItem(elementSlots.get(i), pageItems.get(i));
        }
        return true;
    }

    private List<ItemStack> getPageItems(final int page) {
        return itemPageUtils.getPage(page);
    }

    private void clearSlots() {
        elementSlots.forEach(slot -> inventory.setItem(slot, new ItemStack(Material.AIR)));
    }

}
