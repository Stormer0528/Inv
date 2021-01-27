package de.studiocode.invgui.animation.impl;

import de.studiocode.invgui.InvGui;
import de.studiocode.invgui.animation.Animation;
import de.studiocode.invgui.gui.GUI;
import de.studiocode.invgui.util.SlotUtils;
import de.studiocode.invgui.window.Window;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public abstract class BaseAnimation implements Animation {
    
    private final List<Runnable> finishHandlers = new ArrayList<>();
    private final int tickDelay;
    
    private GUI gui;
    private int width;
    private int height;
    
    private List<Window> windows;
    private CopyOnWriteArrayList<Integer> slots;
    private BiConsumer<Integer, Integer> show;
    private BukkitTask task;
    
    private int frame;
    private int noViewerTicks;
    
    public BaseAnimation(int tickDelay) {
        this.tickDelay = tickDelay;
    }
    
    @Override
    public void setGUI(GUI gui) {
        this.gui = gui;
        this.width = gui.getWidth();
        this.height = gui.getHeight();
    }
    
    @Override
    public void setWindows(@NotNull List<Window> windows) {
        this.windows = windows;
    }
    
    @Override
    public void setSlots(List<Integer> slots) {
        this.slots = new CopyOnWriteArrayList<>(slots);
    }
    
    @Override
    public void addShowHandler(@NotNull BiConsumer<Integer, Integer> show) {
        if (this.show != null) this.show = this.show.andThen(show);
        else this.show = show;
    }
    
    @Override
    public void addFinishHandler(@NotNull Runnable finish) {
        finishHandlers.add(finish);
    }
    
    @Override
    public void start() {
        task = Bukkit.getScheduler().runTaskTimer(InvGui.getInstance().getPlugin(), () -> {
            // if there are now viewers for more than 3 ticks, the animation can be cancelled
            if (getViewers().isEmpty()) {
                noViewerTicks++;
                if (noViewerTicks > 3) {
                    gui.cancelAnimation();
                    return;
                }
            } else noViewerTicks = 0;
    
            // handle the next frame
            handleFrame(frame);
            frame++;
        }, 0, tickDelay);
    }
    
    @Override
    public void cancel() {
        task.cancel();
    }
    
    protected void finish() {
        task.cancel();
        finishHandlers.forEach(Runnable::run);
    }
    
    protected abstract void handleFrame(int frame);
    
    public CopyOnWriteArrayList<Integer> getSlots() {
        return slots;
    }
    
    protected void show(int... slots) {
        for (int i : slots) show.accept(frame, i);
    }
    
    protected int convToIndex(int x, int y) {
        if (x >= width || y >= height)
            throw new IllegalArgumentException("Coordinates out of bounds");
        
        return SlotUtils.convertToIndex(x, y, width);
    }
    
    protected int getWidth() {
        return width;
    }
    
    protected int getHeight() {
        return height;
    }
    
    public List<Player> getViewers() {
        return windows.stream()
            .map(window -> {
                List<HumanEntity> viewers = window.getInventory().getViewers();
                return (Player) (viewers.isEmpty() ? null : viewers.get(0));
            })
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }
    
}
