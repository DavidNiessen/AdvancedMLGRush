package net.skillcode.advancedmlgrush.game.gadgets;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.Getter;
import net.skillcode.advancedmlgrush.annotations.PostConstruct;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Singleton
public class GadgetManager {

    @Inject
    private GadgetFileLoader gadgetFileLoader;

    @Getter
    private final List<Gadget> sticks = new CopyOnWriteArrayList<>();
    @Getter
    private final List<Gadget> blocks = new CopyOnWriteArrayList<>();

    @PostConstruct
    public void loadGadgets() {
        final GadgetsFile gadgetsFile = gadgetFileLoader.getGadgetsFile();

        sticks.addAll(gadgetsFile.getSticks() == null ? DefaultGadgets.getDefaultSticks() : gadgetsFile.getSticks());
        blocks.addAll(gadgetsFile.getBlocks() == null ? DefaultGadgets.getDefaultBlocks() : gadgetsFile.getBlocks());
    }



}
