package cubex2.cs4.plugins.vanilla.tileentity;

import cubex2.cs4.api.TileEntityModule;
import cubex2.cs4.api.TileEntityModuleSupplier;
import cubex2.cs4.plugins.vanilla.crafting.ItemHandlerCrafting;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nullable;

public class TileEntityModuleCrafting implements TileEntityModule
{
    private final ItemHandlerCrafting invHandler;
    private final Supplier supplier;

    public TileEntityModuleCrafting(TileEntity tile, Supplier supplier)
    {
        this.invHandler = new ItemHandlerCrafting(tile, supplier.rows, supplier.columns, supplier.recipeList);
        this.supplier = supplier;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        invHandler.deserializeNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        return invHandler.serializeNBT();
    }

    @Override
    public void setWorld(World world)
    {
        invHandler.setWorld(world);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
    {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY &&
               (facing == null);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
    {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY &&
            (facing == null))
        {
            return (T) invHandler;
        }

        return null;
    }

    public static class Supplier implements TileEntityModuleSupplier
    {
        public int rows = 3;
        public int columns = 3;
        public ResourceLocation recipeList = new ResourceLocation("minecraft", "vanilla");

        @Override
        public TileEntityModule createModule(TileEntity tileEntity)
        {
            return new TileEntityModuleCrafting(tileEntity, this);
        }
    }
}
