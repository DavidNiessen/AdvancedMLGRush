package net.skillcode.advancedmlgrush.inventory.multipage;

import com.google.inject.Inject;
import net.skillcode.advancedmlgrush.annotations.PostConstruct;
import net.skillcode.advancedmlgrush.config.configs.ItemNameConfig;
import net.skillcode.advancedmlgrush.event.EventListener;
import net.skillcode.advancedmlgrush.inventory.AbstractInventory;
import net.skillcode.advancedmlgrush.item.EnumItem;
import net.skillcode.advancedmlgrush.miscellaneous.Constants;
import net.skillcode.advancedmlgrush.util.Pair;
import net.skillcode.advancedmlgrush.util.SkullUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public abstract class MultiPageInventory extends AbstractInventory {

    private final List<Integer> elementSlots = new ArrayList<>();
    private final Map<Player, MultiPageHelper> multiPageHelperMap = new ConcurrentHashMap<>();

    @Inject
    private MPHFactory mphFactory;
    @Inject
    private SkullUtils skullUtils;
    @Inject
    private ItemNameConfig itemNameConfig;

    @PostConstruct
    public void initInventory() {
        super.init();
    }

    protected abstract String title();

    protected abstract LinkedHashMap<ItemStack, Object> onOpen(final @NotNull Player player);

    protected abstract void onElementClick(final Player player, final @NotNull Optional<Object> optional);

    @Override
    protected Pair<Inventory, String> onCreate() {
        final Inventory inventory = Bukkit.createInventory(null, 6 * 9, title());

        final String leftArrowName = itemNameConfig.getString(Optional.empty(), EnumItem.ARROW_LEFT);
        final String rightArrowName = itemNameConfig.getString(Optional.empty(), EnumItem.ARROW_RIGHT);

        inventoryUtils.frame(inventory);
        inventory.setItem(Constants.LEFT_ARROW_SLOT, skullUtils
                .getSkull(Constants.ARROW_LEFT_VALUE, leftArrowName).build());
        inventory.setItem(Constants.RIGHT_ARROW_SLOT, skullUtils
                .getSkull(Constants.ARROW_RIGHT_VALUE, rightArrowName).build());

        elementSlots.addAll(inventoryUtils.getEmptySlots(inventory));
        return new Pair<>(inventory, title());
    }

    @Override
    protected Inventory onOpen(final @NotNull Inventory inventory, final @NotNull Player player) {
        multiPageHelperMap.put(player, mphFactory.create(inventory, elementSlots, onOpen(player)));
        multiPageHelperMap.get(player).loadFirstPage();
        return inventory;
    }

    @Override
    protected List<EventListener<?>> listeners(final @NotNull List<EventListener<?>> eventListeners) {
        final Class<? extends MultiPageInventory> clazz = this.getClass();
        eventListeners.add(new EventListener<InventoryCloseEvent>(InventoryCloseEvent.class) {
            @Override
            protected void onEvent(final @NotNull InventoryCloseEvent event) {
                final Player player = (Player) event.getPlayer();
                if (inventoryUtils.isOpenInventory(player, clazz)) {
                    multiPageHelperMap.remove(player);
                }
            }
        });

        eventListeners.add(new EventListener<InventoryClickEvent>(InventoryClickEvent.class) {
            @Override
            protected void onEvent(final @NotNull InventoryClickEvent event) {
                event.setCancelled(true);
                final Player player = (Player) event.getWhoClicked();
                if (inventoryUtils.isOpenInventory(player, clazz)) {

                    final MultiPageHelper multiPageHelper = multiPageHelperMap.getOrDefault(player, null);
                    final int slot = event.getSlot();

                    if (slot == Constants.LEFT_ARROW_SLOT) {
                        multiPageHelper.loadPreviousPage();
                    } else if (slot == Constants.RIGHT_ARROW_SLOT) {
                        multiPageHelper.loadNextPage();
                    } else if (elementSlots.contains(slot)
                            && itemUtils.isValidItem(event.getCurrentItem())) {
                        onElementClick(player, multiPageHelper.getElement(slot));
                    }
                }
            }
        });
        return eventListeners;
    }

    @Override
    protected boolean cloneOnOpen() {
        return true;
    }
}
