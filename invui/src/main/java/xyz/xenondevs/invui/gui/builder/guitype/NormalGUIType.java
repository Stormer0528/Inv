package xyz.xenondevs.invui.gui.builder.guitype;

import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.gui.builder.GuiContext;
import xyz.xenondevs.invui.gui.impl.NormalGuiImpl;

class NormalGuiType implements GuiType<Gui, Void> {
    
    @Override
    public @NotNull NormalGuiImpl createGui(@NotNull GuiContext<Void> context) {
        NormalGuiImpl gui = new NormalGuiImpl(context.getStructure());
        gui.setBackground(context.getBackground());
        return gui;
    }
    
}
