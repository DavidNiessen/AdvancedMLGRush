package net.skillcode.advancedmlgrush.miscellaneous;

import net.skillcode.advancedmlgrush.util.XMaterial;
import org.bukkit.Material;
import org.fusesource.jansi.Ansi;

public class Constants {

    public static final Material DEFAULT_MATERIAL = XMaterial.STONE.parseMaterial();
    public static final float DEFAULT_SOUND_VOLUME = 1.F;
    public static final float DEFAULT_SOUND_PITCH = 1.F;
    public static final String PLUGIN_PATH = "plugins/MLGRush/";
    /* Paths */
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
    /* Slots */
    public static final int CHALLENGER_SLOT = 0;
    public static final int SETTINGS_SLOT = 2;
    public static final int SPECTATE_SLOT = 4;
    public static final int GADGETS_SLOT = 6;
    public static final int STATS_SLOT = 8;
    public static final int QUEUE_LEAVE_SLOT = 4;
    public static final int LEFT_ARROW_SLOT = 45;
    public static final int RIGHT_ARROW_SLOT = 53;
    /* Messages */
    public static final String INVALID_PORT_MESSAGE = Ansi.ansi().fg(Ansi.Color.RED).boldOff().toString() + "The specified port is invalid, the default port 3306 will be used." + Ansi.ansi().reset();
    public static final String MATERIAL_PARSE_ERROR = Ansi.ansi().fg(Ansi.Color.RED).boldOff().toString() + "An error occurred while parsing material: %s " + Ansi.ansi().reset();
    public static final String SOUND_PARSE_ERROR = Ansi.ansi().fg(Ansi.Color.RED).boldOff().toString() + "An error occurred while parsing sound: %s " + Ansi.ansi().reset();
    public static final String COLOR_PARSE_ERROR = Ansi.ansi().fg(Ansi.Color.RED).boldOff().toString() + "An error occurred while parsing color: %s " + Ansi.ansi().reset();
    public static final String ERROR_MESSAGE = Ansi.ansi().fg(Ansi.Color.RED).boldOff().toString() + "An error occurred: %s" + Ansi.ansi().reset();
    public static final String QUERY_MESSA = "Executing query: " + Ansi.ansi().fg(Ansi.Color.CYAN).boldOff().toString() + "%s" + Ansi.ansi().reset();
    /* Skulls */
    public static final String ARROW_LEFT_VALUE = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzYyNTkwMmIzODllZDZjMTQ3NTc0ZTQyMmRhOGY4ZjM2MWM4ZWI1N2U3NjMxNjc2YTcyNzc3ZTdiMWQifX19";
    public static final String ARROW_RIGHT_VALUE = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDRiZThhZWVjMTE4NDk2OTdhZGM2ZmQxZjE4OWIxNjY0MmRmZjE5ZjI5NTVjMDVkZWFiYTY4YzlkZmYxYmUifX19";
    private Constants() {
    }

}
