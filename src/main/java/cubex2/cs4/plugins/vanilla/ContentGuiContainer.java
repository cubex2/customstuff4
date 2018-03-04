package cubex2.cs4.plugins.vanilla;

import com.google.common.collect.Lists;
import cubex2.cs4.plugins.vanilla.gui.*;
import cubex2.cs4.plugins.vanilla.tileentity.FieldSupplier;
import cubex2.cs4.util.AsmHelper;
import cubex2.cs4.util.ReflectionHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.lang.reflect.Constructor;
import java.util.List;

public class ContentGuiContainer extends ContentGuiBase
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

    public Class<? extends GuiContainerCS4> getGuiClass()
    {
        return guiClass;
    }

    @SideOnly(Side.CLIENT)
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
        if (y < 0)
            return getServerGuiElementForItem(player, world, EnumHand.values()[x]);
        else
            return getServerGuiElementForBlock(player, world, x, y, z);
    }

    @Nullable
    private Object getServerGuiElementForItem(EntityPlayer player, World world, EnumHand hand)
    {
        ItemStack stack = player.getHeldItem(hand);
        if (!stack.isEmpty() && stack.hasCapability(CapabilityItemHandlerSupplier.ITEM_HANDLER_SUPPLIER_CAPABILITY, null))
        {
            return createContainer(stack, player, hand);
        }

        return null;
    }

    @Nullable
    private Object getServerGuiElementForBlock(EntityPlayer player, World world, int x, int y, int z)
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
        if (y < 0)
            return getClientGuiElementForItem(player, world, EnumHand.values()[x]);
        else
            return getClientGuiElementForBlock(player, world, x, y, z);
    }

    @Nullable
    private Object getClientGuiElementForItem(EntityPlayer player, World world, EnumHand hand)
    {
        ItemStack stack = player.getHeldItem(hand);
        if (!stack.isEmpty() && stack.hasCapability(CapabilityItemHandlerSupplier.ITEM_HANDLER_SUPPLIER_CAPABILITY, null))
        {
            return ReflectionHelper.newInstance(guiConstructor, this, createContainer(stack, player, hand), ProgressBarSource.EMPTY, FluidSource.EMPTY);
        }

        return null;
    }

    @Nullable
    private Object getClientGuiElementForBlock(EntityPlayer player, World world, int x, int y, int z)
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
        return new ContainerGui(this, (ItemHandlerSupplier) te, (FluidSource) te, (FieldSupplier) te, player, -1);
    }

    private ContainerGui createContainer(ItemStack stack, EntityPlayer player, EnumHand hand)
    {
        ItemHandlerSupplier itemHandlerSupplier = stack.getCapability(CapabilityItemHandlerSupplier.ITEM_HANDLER_SUPPLIER_CAPABILITY, null);
        int itemSlot = hand == EnumHand.MAIN_HAND ? player.inventory.currentItem : -1;
        return new ContainerGui(this, itemHandlerSupplier, FluidSource.EMPTY, FieldSupplier.EMPTY, player, itemSlot);
    }
}
