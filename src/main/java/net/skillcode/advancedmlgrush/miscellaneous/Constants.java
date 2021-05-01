package net.skillcode.advancedmlgrush.miscellaneous;

import net.skillcode.advancedmlgrush.util.XMaterial;
import net.skillcode.advancedmlgrush.util.XSound;
import org.bukkit.Material;
import org.fusesource.jansi.Ansi;

public class Constants {

    private Constants() {}

    public static final Material DEFAULT_MATERIAL = XMaterial.STONE.parseMaterial();
    public static final XSound DEFAULT_SOUND = XSound.BLOCK_ANVIL_BREAK;
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

    /* Slots */
    public static final int CHALLENGER_SLOT = 0;
    public static final int SETTINGS_SLOT = 2;
    public static final int SPECTATE_SLOT = 4;
    public static final int EXTRAS_SLOT = 6;
    public static final int STATS_SLOT = 8;
    public static final int QUEUE_LEAVE_SLOT = 4;

    /* Messages */
    public static final String INVALID_PORT_MESSAGE = Ansi.ansi().fg(Ansi.Color.RED).boldOff().toString() + "The specified port is invalid, the default port 3306 will be used." + Ansi.ansi().reset();
    public static final String MATERIAL_PARSE_ERROR = Ansi.ansi().fg(Ansi.Color.RED).boldOff().toString() + "An error occurred while parsing material: %s " + Ansi.ansi().reset();
    public static final String SOUND_PARSE_ERROR = Ansi.ansi().fg(Ansi.Color.RED).boldOff().toString() + "An error occurred while parsing sound: %s " + Ansi.ansi().reset();

}
