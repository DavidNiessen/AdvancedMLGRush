package net.skillcode.advancedmlgrush.miscellaneous;

import net.skillcode.advancedmlgrush.util.XMaterial;
import org.bukkit.Material;
import org.fusesource.jansi.Ansi;

public class Constants {

    public static Material DEFAULT_MATERIAL = XMaterial.DIRT.parseMaterial();
    /* Paths */

    public static final String PLUGIN_PATH = "plugins/MLGRush/";
    public static final String DATA_PATH = PLUGIN_PATH + "data/";
    //Databases
    public static final String DATABASE_PATH = DATA_PATH + "data.db";
    //Configs
    public static final String DATA_CONFIG_PATH = DATA_PATH + "data.yml";

    /* Messages */
    public static final String INVALID_PORT_MESSAGE = Ansi.ansi().fg(Ansi.Color.RED).boldOff().toString() + "The specified port is invalid, the default port 3306 will be used." + Ansi.ansi().reset();

}
