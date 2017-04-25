package cubex2.cs4.plugins.vanilla.tileentity;

import cubex2.cs4.api.TileEntityModule;
import cubex2.cs4.api.TileEntityModuleSupplier;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.apache.commons.lang3.ArrayUtils;

import javax.annotation.Nullable;

public class TileEntityModuleInventory implements TileEntityModule
{
    private final ItemStackHandler invHandler;
    private final Supplier supplier;

    public TileEntityModuleInventory(Supplier supplier)
    {
        invHandler = new ItemStackHandler(supplier.size);
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
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
    {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY &&
               ArrayUtils.contains(supplier.sides, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
    {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY &&
            ArrayUtils.contains(supplier.sides, facing))
        {
            return (T) invHandler;
        }

        return null;
    }

    public static class Supplier implements TileEntityModuleSupplier
    {
        public int size;
        public EnumFacing[] sides = EnumFacing.values();

        @Override
        public TileEntityModule createModule(TileEntity tileEntity)
        {
            return new TileEntityModuleInventory(this);
        }
    }
}
