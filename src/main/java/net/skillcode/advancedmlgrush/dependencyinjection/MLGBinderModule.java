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

package net.skillcode.advancedmlgrush.dependencyinjection;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.google.inject.matcher.Matchers;
import com.google.inject.spi.InjectionListener;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;
import net.skillcode.advancedmlgrush.MLGRush;
import net.skillcode.advancedmlgrush.annotations.PostConstruct;
import net.skillcode.advancedmlgrush.inventory.multipage.MPHFactory;
import net.skillcode.advancedmlgrush.item.builder.IBFactory;
import net.skillcode.advancedmlgrush.util.json.JsonConfigAPI;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class MLGBinderModule extends AbstractModule implements TypeListener {

    private final MLGRush mlgRush;

    public MLGBinderModule(final @NotNull MLGRush mlgRush) {
        this.mlgRush = mlgRush;
    }

    @Override
    protected void configure() {
        super.bindListener(Matchers.any(), this);

        super.install(new FactoryModuleBuilder().build(IBFactory.class));
        super.install(new FactoryModuleBuilder().build(MPHFactory.class));

        super.bind(JavaPlugin.class).toInstance(mlgRush);
        super.bind(MLGRush.class).toInstance(mlgRush);
        super.bind(JsonConfigAPI.class).toInstance(new JsonConfigAPI(true));
        super.bind(PluginManager.class).toInstance(mlgRush.getServer().getPluginManager());
    }

    @Override
    public <I> void hear(final @NotNull TypeLiteral<I> typeLiteral, final @NotNull TypeEncounter<I> typeEncounter) {
        typeEncounter.register((InjectionListener<I>) i ->
                Arrays.stream(i.getClass().getMethods()).filter(method -> method.isAnnotationPresent(PostConstruct.class))
                        .forEach(method -> invokeMethod(method, i)));
    }

    private void invokeMethod(final @NotNull Method method, final @NotNull Object object) {
        try {
            method.invoke(object);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
