package cubex2.cs4.plugins.vanilla;

import com.google.common.collect.Lists;
import cubex2.cs4.plugins.vanilla.gui.*;
import cubex2.cs4.plugins.vanilla.tileentity.FieldSupplier;
import cubex2.cs4.util.AsmHelper;
import cubex2.cs4.util.ReflectionHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.objectweb.asm.Opcodes;

import java.lang.reflect.Constructor;
import java.util.List;

public class ContentGuiContainer extends ContentGuiBase implements Opcodes
{
    public int width = 176;
    public int height = 166;
    public List<SlotData> slots = Lists.newArrayList();
    public List<Label> labels = Lists.newArrayList();
    public List<FluidDisplay> fluidDisplays = Lists.newArrayList();
    public List<ShiftClickRule> shiftClickRules = Lists.newArrayList();
    public List<ProgressBar> progressBars = Lists.newArrayList();
    public ResourceLocation bg = null;
    public int bgTexX = 0;
    public int bgTexY = 0;

    private transient Class<? extends GuiContainerCS4> guiClass;
    private transient Constructor<? extends GuiContainerCS4> guiConstructor;

    @SuppressWarnings("unchecked")
    @Override
    protected void init()
    {
        guiClass = AsmHelper.createSubClass(GuiContainerCS4.class, getKey().toString(), 4);
        guiConstructor = ReflectionHelper.getConstructor(guiClass, ContentGuiContainer.class, Container.class, ProgressBarSource.class, FluidSource.class);
    }

    @Override
    protected Object getServerGuiElement(EntityPlayer player, World world, int x, int y, int z)
    {
        TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
        if (te != null && te instanceof ItemHandlerSupplier && te instanceof FieldSupplier)
        {
            return createContainer(te, player);
        }

        return null;
    }

    @Override
    protected Object getClientGuiElement(EntityPlayer player, World world, int x, int y, int z)
    {
        TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
        if (te != null && te instanceof ItemHandlerSupplier && te instanceof FieldSupplier)
        {
            return ReflectionHelper.newInstance(guiConstructor, this, createContainer(te, player), (ProgressBarSource) te, (FluidSource) te);
        }

        return null;
    }

    private ContainerGui createContainer(TileEntity te, EntityPlayer player)
    {
        return new ContainerGui(this, (ItemHandlerSupplier) te, (FluidSource) te, (FieldSupplier) te, player);
    }
}
