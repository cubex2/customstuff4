package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.api.ContentHelper;
import cubex2.cs4.api.InitPhase;
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
        int offsetX;
        int offsetY;

        int getLeft(GuiScreen gui, int elemWidth)
        {
            return GuiHelper.calculateLeft(alignX, offsetX, elemWidth, gui.width);
        }

        int getTop(GuiScreen gui, int elemHeight)
        {
            return GuiHelper.calculateTop(alignY, offsetY, elemHeight, gui.height);
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
        int width = -1;
        int height = -1;

        public EditButton()
        {
            offsetX = Integer.MIN_VALUE;
            offsetY = Integer.MIN_VALUE;
        }

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

            if (width >= 0)
            {
                button.width = width;
            }

            if (height >= 0)
            {
                button.height = height;
            }

            if (offsetX != Integer.MIN_VALUE)
            {
                button.xPosition = getLeft(gui, button.width);
            }

            if (offsetY != Integer.MIN_VALUE)
            {
                button.yPosition = getTop(gui, button.height);
            }
        }
    }
}
