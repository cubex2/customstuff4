package cubex2.cs4.plugins.vanilla;

import com.google.common.collect.Lists;
import cubex2.cs4.plugins.vanilla.gui.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class ContentGuiContainer extends ContentGuiBase
{
    public int width = 176;
    public int height = 166;
    public List<SlotData> slots = Lists.newArrayList();
    public List<Label> labels = Lists.newArrayList();
    public List<ShiftClickRule> shiftClickRules = Lists.newArrayList();
    public ResourceLocation bg = null;
    public int bgTexX = 0;
    public int bgTexY = 0;

    @Override
    protected Object getServerGuiElement(EntityPlayer player, World world, int x, int y, int z)
    {
        TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
        if (te != null && te instanceof ItemHandlerSupplier)
        {
            return new ContainerGui(this, (ItemHandlerSupplier) te, player);
        }

        return null;
    }

    @Override
    protected Object getClientGuiElement(EntityPlayer player, World world, int x, int y, int z)
    {
        TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
        if (te != null && te instanceof ItemHandlerSupplier)
        {
            return new GuiContainerCS4(this, new ContainerGui(this, (ItemHandlerSupplier) te, player));
        }

        return null;
    }
}
