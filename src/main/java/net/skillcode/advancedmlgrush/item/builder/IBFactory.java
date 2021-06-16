package net.skillcode.advancedmlgrush.item.builder;

import org.jetbrains.annotations.NotNull;

public interface IBFactory {

    ItemBuilder create(final @NotNull MetaType metaType, final int data);

}
