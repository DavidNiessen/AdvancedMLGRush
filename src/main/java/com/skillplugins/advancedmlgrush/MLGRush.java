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

package com.skillplugins.advancedmlgrush;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.skillplugins.advancedmlgrush.api.APIImplementation;
import com.skillplugins.advancedmlgrush.api.MLGRushAPI;
import com.skillplugins.advancedmlgrush.command.CommandInitializer;
import com.skillplugins.advancedmlgrush.config.FileInitializer;
import com.skillplugins.advancedmlgrush.dependencyinjection.MLGBinderModule;
import com.skillplugins.advancedmlgrush.event.EventHandlerInitializer;
import com.skillplugins.advancedmlgrush.game.map.MapManager;
import com.skillplugins.advancedmlgrush.game.map.schematic.compressor.CompressorManager;
import com.skillplugins.advancedmlgrush.game.map.schematic.serializer.SerializerManager;
import com.skillplugins.advancedmlgrush.item.overwriter.ItemOWInitializer;
import com.skillplugins.advancedmlgrush.listener.ListenerInitializer;
import com.skillplugins.advancedmlgrush.miscellaneous.Constants;
import com.skillplugins.advancedmlgrush.miscellaneous.registrable.RegistrableInitializer;
import com.skillplugins.advancedmlgrush.miscellaneous.update.UpdateChecker;
import com.skillplugins.advancedmlgrush.placeholder.PlaceholderInitializer;
import com.skillplugins.advancedmlgrush.protocol.ProtocolInitializer;
import com.skillplugins.advancedmlgrush.sql.ConnectionManager;
import com.skillplugins.advancedmlgrush.sql.DataInitializer;
import com.skillplugins.advancedmlgrush.util.PlayerUtils;
import com.skillplugins.advancedmlgrush.util.WorldUtils;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

@Singleton
public class MLGRush extends JavaPlugin {

    @Getter
    private static MLGRushAPI api;

    @Inject
    private DataInitializer dataInitializer;
    @Inject
    private PlaceholderInitializer placeholderInitializer;
    @Inject
    private FileInitializer fileInitializer;
    @Inject
    private ItemOWInitializer itemOWInitializer;
    @Inject
    private ListenerInitializer listenerInitializer;
    @Inject
    private CommandInitializer commandInitializer;
    @Inject
    private RegistrableInitializer registrableInitializer;
    @Inject
    private EventHandlerInitializer eventHandlerInitializer;
    @Inject
    private ConnectionManager connectionManager;
    @Inject
    private CompressorManager compressorManager;
    @Inject
    private SerializerManager serializerManager;
    @Inject
    private MapManager mapManager;
    @Inject
    private PlayerUtils playerUtils;
    @Inject
    private UpdateChecker updateChecker;
    @Inject
    private ProtocolInitializer protocolInitializer;

    @Getter
    private UUID uuid;

    @Override
    public void onEnable() {
        getLogger().info(Constants.STARTUP_MESSAGE);
        final long millis = System.currentTimeMillis();

        uuid = UUID.randomUUID();
        final Injector injector = Guice.createInjector(new MLGBinderModule(this));
        injector.injectMembers(this);

        dataInitializer.init(injector);
        placeholderInitializer.init(injector);
        fileInitializer.init(injector);
        itemOWInitializer.init(injector);
        registrableInitializer.init(injector);
        listenerInitializer.init(injector);
        eventHandlerInitializer.init(injector);
        commandInitializer.init(injector);
        compressorManager.init(injector);
        serializerManager.init(injector);
        mapManager.init(injector);
        protocolInitializer.init(injector);

        WorldUtils.deleteWorlds();
        api = injector.getInstance(APIImplementation.class);

        getLogger().info(String.format(Constants.AFTER_STARTUP_MESSAGE, System.currentTimeMillis() - millis));
        updateChecker.checkForUpdates();
    }

    @Override
    public void onDisable() {
        playerUtils.restartKick();
        connectionManager.closeConnections();
    }

}
