package cubex2.cs4.plugins.vanilla.gui;

import cubex2.cs4.plugins.vanilla.ContentGuiContainer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.text.TextComponentTranslation;
import org.lwjgl.opengl.GL11;

public class GuiContainerCS4 extends GuiContainer
{
    private final ContentGuiContainer content;
    private final ProgressBarSource progressBarSource;

    public GuiContainerCS4(ContentGuiContainer content, Container container, ProgressBarSource progressBarSource)
    {
        super(container);

        this.content = content;
        this.progressBarSource = progressBarSource;

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

            GL11.glPushMatrix();
            GL11.glTranslatef(x, y, 0f);
            for (ProgressBar bar : content.progressBars)
            {
                bar.draw(progressBarSource);
            }
            GL11.glPopMatrix();
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
