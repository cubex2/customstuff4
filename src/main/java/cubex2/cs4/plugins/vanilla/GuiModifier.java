package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.api.ContentHelper;
import cubex2.cs4.api.InitPhase;
import cubex2.cs4.api.Length;
import cubex2.cs4.data.SimpleContent;
import cubex2.cs4.util.GuiHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

class GuiModifier extends SimpleContent
{
    String gui;
    List<Label> labels = Collections.emptyList();
    List<Integer> removeButtons = Collections.emptyList();
    List<EditButton> editButtons = Collections.emptyList();

    @Override
    protected void doInit(InitPhase phase, ContentHelper helper)
    {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    protected boolean isReady()
    {
        return true;
    }

    @SubscribeEvent
    void onPostInitGui(GuiScreenEvent.InitGuiEvent.Post event)
    {
        if (isCorrectGui(event.getGui()))
        {
            event.getButtonList().removeIf(button -> removeButtons.contains(button.id));
            editButtons.forEach(b -> b.postInit(event));
        }
    }

    @SubscribeEvent
    void onRenderGui(GuiScreenEvent.DrawScreenEvent.Post event)
    {
        if (isCorrectGui(event.getGui()))
        {
            labels.forEach(m -> m.render(event));
        }
    }

    private boolean isCorrectGui(GuiScreen screen)
    {
        Class<? extends GuiScreen> guiClass = GuiMapping.getGuiClass(gui.toLowerCase());
        return guiClass != null && guiClass.isAssignableFrom(screen.getClass());
    }

    interface Modifier
    {
        default void render(GuiScreenEvent.DrawScreenEvent.Post event) {}
    }

    abstract static class PositionedModifier implements Modifier
    {
        String alignX = "left";
        String alignY = "top";
        Length offsetX = Length.ZERO;
        Length offsetY = Length.ZERO;

        int getLeft(GuiScreen gui, int elemWidth)
        {
            int offset = offsetX.getLength(gui.width);
            return GuiHelper.calculateLeft(alignX, offset, elemWidth, gui.width);
        }

        int getTop(GuiScreen gui, int elemHeight)
        {
            int offset = offsetY.getLength(gui.height);
            return GuiHelper.calculateTop(alignY, offset, elemHeight, gui.height);
        }
    }

    static class Label extends PositionedModifier
    {
        String text;
        boolean dropShadow = true;

        @Override
        public void render(GuiScreenEvent.DrawScreenEvent.Post event)
        {
            FontRenderer font = Minecraft.getMinecraft().fontRendererObj;

            int left = getLeft(event.getGui(), font.getStringWidth(text));
            int top = getTop(event.getGui(), font.FONT_HEIGHT);

            font.drawString(text, left, top, 0xFFFFFFFF, dropShadow);
        }
    }

    static class EditButton extends PositionedModifier
    {
        int buttonId;
        String text = null;
        Length width = Length.ZERO;
        Length height = Length.ZERO;

        void postInit(GuiScreenEvent.InitGuiEvent.Post event)
        {
            Optional<GuiButton> button = event.getButtonList().stream().filter(b -> b.id == buttonId).findFirst();
            button.ifPresent(b -> modifyButton(event.getGui(), b));
        }

        private void modifyButton(GuiScreen gui, GuiButton button)
        {
            if (text != null)
            {
                button.displayString = text;
            }

            if (width != Length.ZERO)
            {
                button.width = width.getLength(gui.width);
            }

            if (height != Length.ZERO)
            {
                button.height = height.getLength(gui.height);
            }

            if (offsetX != Length.ZERO)
            {
                button.xPosition = getLeft(gui, button.width);
            }

            if (offsetY != Length.ZERO)
            {
                button.yPosition = getTop(gui, button.height);
            }
        }
    }
}
