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

package com.skillplugins.advancedmlgrush.util;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ListBuilder<T> {

    private final List<T> list;

    protected ListBuilder(final @NotNull List<T> list) {
        this.list = list;
    }

    protected ListBuilder() {
        this.list = new ArrayList<T>();
    }

    public static <T> ListBuilder<T> create(final @NotNull Class<? extends T> clazz) {
        return new ListBuilder<T>();
    }

    public List<T> build() {
        return list;
    }

    public ListBuilder<T> add(final @NotNull T t) {
        list.add(t);
        return this;
    }

    @SafeVarargs
    public final ListBuilder<T> add(final @NotNull T... t) {
        list.addAll(Arrays.asList(t));
        return this;
    }

    public ListBuilder<T> add(final @NotNull Iterable<T> iterable) {
        iterable.forEach(list::add);
        return this;
    }

    public ListBuilder<T> add(final @NotNull Collection<T> collection) {
        list.addAll(collection);
        return this;
    }

}
