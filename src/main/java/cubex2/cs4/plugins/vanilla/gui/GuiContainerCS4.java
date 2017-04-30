package cubex2.cs4.plugins.vanilla.gui;

import cubex2.cs4.plugins.vanilla.ContentGuiContainer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.text.TextComponentTranslation;

public class GuiContainerCS4 extends GuiContainer
{
    private final ContentGuiContainer content;

    public GuiContainerCS4(ContentGuiContainer content, Container container)
    {
        super(container);

        this.content = content;

        xSize = content.width;
        ySize = content.height;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        if (content.bg != null)
        {
            mc.getTextureManager().bindTexture(content.bg);
            int x = (width - xSize) / 2;
            int y = (height - ySize) / 2;
            drawTexturedModalRect(x, y, content.bgTexX, content.bgTexY, xSize, ySize);
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        for (Label label : content.labels)
        {
            fontRendererObj.drawString(new TextComponentTranslation(label.text).getUnformattedText(), label.x, label.y, label.color.getRGB(), label.dropShadow);
        }
    }
}
