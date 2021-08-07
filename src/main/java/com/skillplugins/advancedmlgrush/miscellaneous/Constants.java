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

package com.skillplugins.advancedmlgrush.miscellaneous;

import com.skillplugins.advancedmlgrush.lib.xseries.XMaterial;
import org.bukkit.Material;
import org.fusesource.jansi.Ansi;

public class Constants {

    public static final int RESOURCE_ID = 93927;

    public static final Material DEFAULT_MATERIAL = XMaterial.STONE.parseMaterial();
    public static final Material DEFAULT_MAP_MATERIAL = XMaterial.MAP.parseMaterial();
    public static final float DEFAULT_SOUND_VOLUME = 1.F;
    public static final float DEFAULT_SOUND_PITCH = 1.F;
    /* Paths */
    public static final String PLUGIN_PATH = "plugins/MLGRush/";
    public static final String DATA_PATH = PLUGIN_PATH + "data/";
    //Databases
    public static final String DATABASE_PATH = DATA_PATH + "data.db";
    public static final String TABLE_NAME = "mlgrush";
    //Configs
    public static final String MAIN_CONFIG_PATH = PLUGIN_PATH + "config.yml";
    public static final String DATA_CONFIG_PATH = DATA_PATH + "data.yml";
    public static final String MESSAGE_CONFIG_PATH = PLUGIN_PATH + "messages.yml";
    public static final String ITEM_CONFIG_PATH = PLUGIN_PATH + "items/";
    public static final String ITEM_MATERIAL_CONFIG_PATH = ITEM_CONFIG_PATH + "materials.yml";
    public static final String ITEM_NAME_CONFIG_PATH = ITEM_CONFIG_PATH + "names.yml";
    public static final String SOUND_CONFIG_PATH = PLUGIN_PATH + "sounds.yml";
    public static final String GADGETS_CONFIG_PATH = PLUGIN_PATH + "gadgets.json";
    public static final String INVENTORY_CONFIG_PATH = PLUGIN_PATH + "inventories.yml";
    public static final String DEBUG_CONFIG_PATH = PLUGIN_PATH + "debug.yml";
    public static final String SPAWN_FILE_PATH = PLUGIN_PATH + "spawn.json";
    public static final String SCOREBOARD_CONFIG_PATH = PLUGIN_PATH + "scoreboards.yml";
    public static final String SLOTS_CONFIG_PATH = ITEM_CONFIG_PATH + "slots.yml";
    public static final String COMMAND_CONFIG_PATH = PLUGIN_PATH + "commands.yml";
    //Maps
    public static final String MAP_PATH = PLUGIN_PATH + "maps/";
    //Worlds
    public static final String WORLD_PATH = PLUGIN_PATH + "worlds/";

    /* Messages */
    public static final String MLGRUSH_COMMAND_MESSAGE = "&eAdvancedMLGRush &7version &e%s &7by &eSkillCode&7.";
    public static final String STARTUP_MESSAGE = "\n" + Ansi.ansi().fg(Ansi.Color.YELLOW) +
            "=============================================================================================================\n" +
            "             _                               _ __  __ _      _____ _____           _     \n" +
            "    /\\      | |                             | |  \\/  | |    / ____|  __ \\         | |    \n" +
            "   /  \\   __| |_   ____ _ _ __   ___ ___  __| | \\  / | |   | |  __| |__) |   _ ___| |__  \n" +
            "  / /\\ \\ / _` \\ \\ / / _` | '_ \\ / __/ _ \\/ _` | |\\/| | |   | | |_ |  _  / | | / __| '_ \\ \n" +
            " / ____ \\ (_| |\\ V / (_| | | | | (_|  __/ (_| | |  | | |___| |__| | | \\ \\ |_| \\__ \\ | | |\n" +
            "/_/    \\_\\__,_| \\_/ \\__,_|_| |_|\\___\\___|\\__,_|_|  |_|______\\_____|_|  \\_\\__,_|___/_| |_| by SkillCode\n" +
            "==============================================================================================================="
            + Ansi.ansi().reset();
    public static final String OFFLINE_MODE = Ansi.ansi().fg(Ansi.Color.RED).boldOff().toString() + "WARNING: This server is running in offline/cracked mode. Make sure that each player has a unique name and UUID." + Ansi.ansi().reset();
    public static final String AFTER_STARTUP_MESSAGE = "Successfully loaded in %s ms!";
    public static final String INVALID_PORT_MESSAGE = Ansi.ansi().fg(Ansi.Color.RED).boldOff().toString() + "The specified port is invalid, the default port 3306 will be used." + Ansi.ansi().reset();
    public static final String MATERIAL_PARSE_ERROR = Ansi.ansi().fg(Ansi.Color.RED).boldOff().toString() + "An error occurred while parsing material: %s " + Ansi.ansi().reset();
    public static final String SOUND_PARSE_ERROR = Ansi.ansi().fg(Ansi.Color.RED).boldOff().toString() + "An error occurred while parsing sound: %s " + Ansi.ansi().reset();
    public static final String SPAWN_FILE_DELETE_ERROR = Ansi.ansi().fg(Ansi.Color.RED).boldOff().toString() + "Cannot delete spawn file. Please delete it manually." + Ansi.ansi().reset();
    public static final String ERROR_MESSAGE = Ansi.ansi().fg(Ansi.Color.RED).boldOff().toString() + "An error occurred: %s" + Ansi.ansi().reset();
    public static final String QUERY_MESSAGE = "Executing query: " + Ansi.ansi().fg(Ansi.Color.CYAN).boldOff().toString() + "%s" + Ansi.ansi().reset();
    public static final String MAP_LOADED = "Map loaded: %1$s, took %2$sms.";
    public static final String INVALID_SERIALIZER = Ansi.ansi().fg(Ansi.Color.RED).boldOff().toString() + "Invalid serializer: %s" + Ansi.ansi().reset();
    public static final String INVALID_COMPRESSOR = Ansi.ansi().fg(Ansi.Color.RED).boldOff().toString() + "Invalid compressor: %s" + Ansi.ansi().reset();
    public static final String UPDATE_CHECKER = "Checking for updates...";
    public static final String UPDATE_CHECKER_AVAILABLE = Ansi.ansi().fg(Ansi.Color.YELLOW).boldOff().toString() + "A new update is available: %s" + Ansi.ansi().reset();
    public static final String UPDATE_CHECKER_ERROR = Ansi.ansi().fg(Ansi.Color.RED).boldOff().toString() + "An error occurred while checking for updates: %s" + Ansi.ansi().reset();
    public static final String PROTOCOLLIB_NOT_FOUND_MESSAGE = Ansi.ansi().fg(Ansi.Color.RED).boldOff().toString() + "WARNING: ProtocolLib not found." + Ansi.ansi().reset();
    /* Skulls */
    public static final String ARROW_LEFT_VALUE = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzYyNTkwMmIzODllZDZjMTQ3NTc0ZTQyMmRhOGY4ZjM2MWM4ZWI1N2U3NjMxNjc2YTcyNzc3ZTdiMWQifX19";
    public static final String ARROW_RIGHT_VALUE = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDRiZThhZWVjMTE4NDk2OTdhZGM2ZmQxZjE4OWIxNjY0MmRmZjE5ZjI5NTVjMDVkZWFiYTY4YzlkZmYxYmUifX19";
    public static final String RANDOM_ITEM_VALUE = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmFkYzA0OGE3Y2U3OGY3ZGFkNzJhMDdkYTI3ZDg1YzA5MTY4ODFlNTUyMmVlZWQxZTNkYWYyMTdhMzhjMWEifX19";

    private Constants() {
    }

}
