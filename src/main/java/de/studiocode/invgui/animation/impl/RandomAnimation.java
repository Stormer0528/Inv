package de.studiocode.invgui.animation.impl;

import de.studiocode.invgui.item.Item;
import org.bukkit.Sound;

import java.util.List;
import java.util.Random;

/**
 * Lets the {@link Item}s pop up randomly.
 */
public class RandomAnimation extends BaseAnimation {
    
    private final Random random = new Random();
    
    public RandomAnimation(int tickDelay, boolean sound) {
        super(tickDelay);
        
        if (sound) addShowHandler((frame, index) -> getPlayer().playSound(getPlayer().getLocation(),
            Sound.ENTITY_ITEM_PICKUP, 1, 1));
    }
    
    @Override
    protected void handleFrame(int frame) {
        List<Integer> slots = getSlots();
        
        if (!slots.isEmpty()) {
            int slot = slots.get(random.nextInt(slots.size()));
            slots.remove(Integer.valueOf(slot));
            show(slot);
        } else finished();
    }
    
}
