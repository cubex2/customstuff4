package cubex2.cs4.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.opengl.GL11;

public class RenderHelper
{
    public static void renderGuiFluid(FluidStack fluid, float fill, int x, int y, int width, int maxHeight)
    {
        if (fluid != null && fluid.getFluid() != null && fluid.amount > 0)
        {
            TextureAtlasSprite sprite = getStillTexture(fluid);
            if (sprite != null)
            {
                int rendHeight = (int) Math.max(Math.min((float) maxHeight, (float) maxHeight * fill), 1.0F);
                int yPos = y + maxHeight - rendHeight;
                Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
                int fluidColor = fluid.getFluid().getColor(fluid);
                GL11.glColor3ub((byte) (fluidColor >> 16 & 255), (byte) (fluidColor >> 8 & 255), (byte) (fluidColor & 255));
                GlStateManager.enableBlend();

                for (int i = 0; i < width; i += 16)
                {
                    for (int j = 0; j < rendHeight; j += 16)
                    {
                        int dwt = Math.min(width - i, 16);
                        int dht = Math.min(rendHeight - j, 16);
                        int dx = x + i;
                        int dy = yPos + j;
                        double minU = (double) sprite.getMinU();
                        double maxU = (double) sprite.getMaxU();
                        double minV = (double) sprite.getMinV();
                        double maxV = (double) sprite.getMaxV();
                        Tessellator tessellator = Tessellator.getInstance();
                        BufferBuilder tes = tessellator.getBuffer();
                        tes.begin(7, DefaultVertexFormats.POSITION_TEX);
                        tes.pos((double) dx, (double) (dy + dht), 0.0D).tex(minU, minV + (maxV - minV) * (double) dht / 16.0D).endVertex();
                        tes.pos((double) (dx + dwt), (double) (dy + dht), 0.0D).tex(minU + (maxU - minU) * (double) dwt / 16.0D, minV + (maxV - minV) * (double) dht / 16.0D).endVertex();
                        tes.pos((double) (dx + dwt), (double) dy, 0.0D).tex(minU + (maxU - minU) * (double) dwt / 16.0D, minV).endVertex();
                        tes.pos((double) dx, (double) dy, 0.0D).tex(minU, minV).endVertex();
                        tessellator.draw();
                    }
                }

                GlStateManager.disableBlend();
            }
        }
    }

    public static TextureAtlasSprite getStillTexture(FluidStack fluid)
    {
        return fluid != null && fluid.getFluid() != null ? getStillTexture(fluid.getFluid()) : null;
    }

    public static TextureAtlasSprite getStillTexture(Fluid fluid)
    {
        ResourceLocation tex = fluid.getStill();
        return tex == null ? null : Minecraft.getMinecraft().getTextureMapBlocks().getTextureExtry(tex.toString());
    }
}
