package de.studiocode.invgui.animation.impl;

public class VerticalSnakeAnimation extends SoundAnimation {
    
    private int x;
    private int y;
    private boolean up;
    
    public VerticalSnakeAnimation(int tickDelay, boolean sound) {
        super(tickDelay, sound);
    }
    
    @Override
    protected void handleFrame(int frame) {
        boolean slotShown = false;
        while (!slotShown) {
            int slotIndex = convToIndex(x, y);
            if (slotShown = getSlots().contains(slotIndex)) show(slotIndex);
            
            if (up) {
                if (y <= 0) {
                    x++;
                    y = 0;
                    up = false;
                } else y--;
            } else {
                if (y >= getHeight() - 1) {
                    x++;
                    up = true;
                } else y++;
            }
            
            if (x >= getWidth()) {
                finish();
                return;
            }
        }
    }
    
}
