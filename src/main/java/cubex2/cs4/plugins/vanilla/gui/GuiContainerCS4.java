package cubex2.cs4.plugins.vanilla.gui;

import cubex2.cs4.plugins.vanilla.ContentGuiContainer;
import cubex2.cs4.util.RenderHelper;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Container;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import org.lwjgl.opengl.GL11;

import java.util.Collections;

public class GuiContainerCS4 extends GuiContainer
{
    private final ContentGuiContainer content;
    private final ProgressBarSource progressBarSource;
    private final FluidSource fluidSource;

    public GuiContainerCS4(ContentGuiContainer content, Container container, ProgressBarSource progressBarSource, FluidSource fluidSource)
    {
        super(container);

        this.content = content;
        this.progressBarSource = progressBarSource;
        this.fluidSource = fluidSource;

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
            fontRenderer.drawString(new TextComponentTranslation(label.text).getUnformattedText(), label.x, label.y, label.color.getRGB(), label.dropShadow);
        }

        for (FluidDisplay display : content.fluidDisplays)
        {
            IFluidTank tank = fluidSource.getFluidTank(display.source);
            if (tank != null)
            {
                FluidStack fluid = tank.getFluid();
                float fluidLevel = fluid == null ? 0.0F : (float) tank.getFluidAmount() / (float) tank.getCapacity();
                GlStateManager.color(1f, 1f, 1f, 1f);
                RenderHelper.renderGuiFluid(fluid, fluidLevel, display.x, display.y, display.width, display.height);
                GlStateManager.color(1f, 1f, 1f, 1f);
            }

            if (display.overlayTexX >= 0)
            {
                mc.getTextureManager().bindTexture(content.bg);

                drawTexturedModalRect(display.x, display.y, display.overlayTexX, display.overlayTexY, display.width, display.height);
            }
        }

        for (FluidDisplay display : content.fluidDisplays)
        {
            IFluidTank tank = fluidSource.getFluidTank(display.source);

            if (mouseX >= guiLeft + display.x && mouseX <= guiLeft + display.x + display.width
                && mouseY >= guiTop + display.y && mouseY <= guiTop + display.y + display.height)
            {
                int amount = tank == null ? 0 : tank.getFluidAmount();
                int capacity = tank == null ? 0 : tank.getCapacity();
                drawHoveringText(Collections.singletonList(String.format("%d / %d mB", amount, capacity)), mouseX - guiLeft, mouseY - guiTop);
            }
        }
    }
}
