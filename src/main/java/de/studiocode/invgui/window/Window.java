package de.studiocode.invgui.window;

import de.studiocode.invgui.animation.Animation;
import de.studiocode.invgui.gui.GUI;
import de.studiocode.invgui.gui.SlotElement.ItemStackHolder;
import de.studiocode.invgui.item.Item;
import de.studiocode.invgui.item.itembuilder.ItemBuilder;
import de.studiocode.invgui.virtualinventory.VirtualInventory;
import de.studiocode.invgui.window.impl.BaseWindow;
import de.studiocode.invgui.window.impl.DropperWindow;
import de.studiocode.invgui.window.impl.HopperWindow;
import de.studiocode.invgui.window.impl.NormalInventoryWindow;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;
import java.util.function.Predicate;

/**
 * A window is the way to show a player a GUI.
 * Windows can only have one viewer at a time.
 *
 * @see BaseWindow
 * @see NormalInventoryWindow
 * @see HopperWindow
 * @see DropperWindow
 */
public interface Window {
    
    /**
     * Gets the underlying {@link Inventory}.
     *
     * @return The underlying {@link Inventory}.
     */
    Inventory getInventory();
    
    /**
     * Gets the underlying {@link GUI}.
     *
     * @return The underlying {@link GUI}.
     */
    GUI getGui();
    
    /**
     * A method called every tick by the {@link WindowManager}
     * to re-draw the {@link Item}s.
     */
    void handleTick();
    
    /**
     * A method called by the {@link WindowManager} to notify the Window
     * that one of its {@link Item}s has been clicked.
     *
     * @param event The {@link InventoryClickEvent} associated with this action.
     */
    void handleClick(InventoryClickEvent event);
    
    /**
     * A method called by the {@link WindowManager} to notify the Window
     * that {@link ItemStack}s have been shift-clicked from the lower
     * {@link Inventory} to this {@link Window}
     */
    void handleItemShift(InventoryClickEvent event);
    
    /**
     * A method called by the {@link WindowManager} to notify the Window
     * that its underlying {@link Inventory} is being opened.
     *
     * @param event The {@link InventoryOpenEvent} associated with this action.
     */
    void handleOpen(InventoryOpenEvent event);
    
    /**
     * A method called by the {@link WindowManager} to notify the Window
     * that its underlying {@link Inventory} is being closed.
     *
     * @param player The {@link Player} who closed this inventory.
     */
    void handleClose(Player player);
    
    /**
     * A method called by the {@link Item} itself to notify the Window
     * that its {@link ItemBuilder} has been updated and the {@link ItemStack}
     * in the {@link Inventory} should be replaced.
     *
     * @param item The {@link Item} whose {@link ItemBuilder} has been updated.
     */
    void handleItemBuilderUpdate(Item item);
    
    /**
     * A method called by the {@link VirtualInventory} to notify the
     * Window that one if it's contents has been updated and the {@link ItemStack}'s
     * displayed in the {@link Inventory} should be replaced.
     *
     * @param virtualInventory The {@link VirtualInventory}
     */
    void handleVirtualInventoryUpdate(VirtualInventory virtualInventory);
    
    /**
     * Removes the {@link Window} from the {@link WindowManager} list.
     * If this method is called, the {@link Window} can't be shown again.
     *
     * @param closeForViewer If the underlying {@link Inventory} should be closed for the viewer.
     */
    void close(boolean closeForViewer);
    
    /**
     * Gets if the {@link Window} is closed and can't be shown again.
     *
     * @return If the {@link Window} is closed.
     */
    boolean isClosed();
    
    /**
     * Closes the underlying {@link Inventory} for its viewer.
     */
    void closeForViewer();
    
    /**
     * Shows the window to a the player.
     */
    void show();
    
    /**
     * Plays an animation.
     *
     * @param animation The animation to play.
     * @param filter    The filter that selects which Items should be animated.
     */
    void playAnimation(@NotNull Animation animation, @Nullable Predicate<ItemStackHolder> filter);
    
    /**
     * Gets if the player is able to close the {@link Inventory}.
     *
     * @return If the player is able to close the {@link Inventory}.
     */
    boolean isCloseable();
    
    /**
     * Sets if the player should be able to close the {@link Inventory}.
     *
     * @param closeable If the player should be able to close the {@link Inventory}.
     */
    void setCloseable(boolean closeable);
    
    /**
     * Gets the viewer of this {@link Window}
     *
     * @return The viewer of this window, can be null.
     */
    Player getViewer();
    
    /**
     * Gets the viewer's UUID or null if there is no viewer.
     *
     * @return The viewer's UUID or null if there is now viewer.
     */
    UUID getViewerUUID();
    
}
