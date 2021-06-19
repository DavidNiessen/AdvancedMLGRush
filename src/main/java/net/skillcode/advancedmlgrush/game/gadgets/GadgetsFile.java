package net.skillcode.advancedmlgrush.game.gadgets;

import lombok.Getter;
import net.skillcode.advancedmlgrush.util.json.JsonConfig;

import java.util.List;

@Getter
public class GadgetsFile implements JsonConfig {

    private List<Gadget> sticks = DefaultGadgets.getDefaultSticks();
    private List<Gadget> blocks = DefaultGadgets.getDefaultBlocks();

}
