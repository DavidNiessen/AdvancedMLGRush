package net.skillcode.advancedmlgrush.item.overwriter;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.skillcode.advancedmlgrush.item.overwriter.overwriters.StatsItemOW;

@Singleton
public class ItemOWRegistry {

    @Inject
    private ItemOWManager itemOWManager;
    @Inject
    private StatsItemOW statsItemOW;

    public void registerOWs() {
        itemOWManager.registerItemOW(statsItemOW);
    }
}
