package de.studiocode.invgui.window.impl.combined.combinedgui;

import de.studiocode.invgui.gui.GUI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class SimpleCombinedGUIWindow extends CombinedGUIWindow {
    
    public SimpleCombinedGUIWindow(Player player, String title, GUI gui, boolean closeable, boolean closeOnEvent) {
        super(player, gui, createInventory(gui, title), closeable, closeOnEvent);
    }
    
    private static Inventory createInventory(GUI gui, String title) {
        if (gui.getWidth() != 9)
            throw new IllegalArgumentException("GUI width has to be 9");
        if (gui.getHeight() <= 4)
            throw new IllegalArgumentException("GUI height has to be bigger than 4");
        
        return Bukkit.createInventory(null, gui.getSize() - 36, title);
    }
    
}
