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

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PageUtils<T> extends ArrayList<T> {

    private final int pageSize;

    public PageUtils(final int pageSize) {
        this(pageSize, new ArrayList<>());
    }

    @SafeVarargs
    public PageUtils(final int pageSize,
                     final @NotNull T... objects) {
        this(pageSize, Arrays.asList(objects));
    }

    public PageUtils(final int pageSize,
                     final @NotNull List<T> objects) {
        this.pageSize = pageSize;
        addAll(objects);
    }

    public int pageSize() {
        return pageSize;
    }

    public int totalPages() {
        return (int) Math.ceil((double) size() / pageSize);
    }

    public boolean exists(final int page) {
        return !(page < 0) && page < totalPages();
    }

    public List<T> getPage(final int page) {
        if (page < 0 || page >= totalPages()) return new ArrayList<>();

        final int min = page * pageSize;
        int max = ((page * pageSize) + pageSize);

        if (max > size()) max = size();

        return subList(min, max);
    }
}
