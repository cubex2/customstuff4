package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.api.ContentHelper;
import cubex2.cs4.api.InitPhase;
import cubex2.cs4.data.SimpleContent;
import cubex2.cs4.util.GuiHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

class GuiModifier extends SimpleContent
{
    private static final String GUI_MAIN_MENU = "mainmenu";

    String gui;
    List<Label> labels;

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
    void onRenderGui(GuiScreenEvent.DrawScreenEvent event)
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
        void render(GuiScreenEvent.DrawScreenEvent event);
    }

    abstract static class PositionedModifier implements Modifier
    {
        String alignX = "left";
        String alignY = "top";
        int offsetX;
        int offsetY;

        protected int getLeft(GuiScreen gui)
        {
            FontRenderer font = Minecraft.getMinecraft().fontRendererObj;

            return GuiHelper.calculateLeft(alignX, offsetX, font.getStringWidth("text"), gui.width);
        }

        protected int getTop(GuiScreen gui)
        {
            FontRenderer font = Minecraft.getMinecraft().fontRendererObj;

            return GuiHelper.calculateTop(alignY, offsetY, font.FONT_HEIGHT, gui.height);
        }
    }

    static class Label extends PositionedModifier
    {
        String text;
        boolean dropShadow = true;

        @Override
        public void render(GuiScreenEvent.DrawScreenEvent event)
        {
            int left = getLeft(event.getGui());
            int top = getTop(event.getGui());

            Minecraft.getMinecraft().fontRendererObj.drawString(text, left, top, 0xFFFFFFFF, dropShadow);
        }
    }
}
