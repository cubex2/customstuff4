package cubex2.cs4.plugins.vanilla.tileentity;

import cubex2.cs4.api.TileEntityModule;
import cubex2.cs4.api.TileEntityModuleSupplier;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import org.apache.commons.lang3.ArrayUtils;

import javax.annotation.Nullable;

public class TileEntityModuleTank implements TileEntityModule
{
    private final Supplier supplier;
    private final FluidTank tank;

    public TileEntityModuleTank(Supplier supplier, TileEntity tile)
    {
        this.supplier = supplier;
        tank = new FluidTank(supplier.capacity);
        tank.setCanDrain(supplier.canDrain);
        tank.setCanFill(supplier.canFill);
        tank.setTileEntity(tile);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        tank.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        return tank.writeToNBT(compound);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
    {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
        {
            return facing == null || ArrayUtils.contains(supplier.sides, facing);
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
    {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
        {
            if (facing == null || ArrayUtils.contains(supplier.sides, facing))
                return (T) tank;
        }
        return null;
    }

    public static class Supplier implements TileEntityModuleSupplier
    {
        public int capacity = 10000;
        public boolean canDrain = true;
        public boolean canFill = true;
        public EnumFacing[] sides = EnumFacing.values();

        @Override
        public TileEntityModule createModule(TileEntity tileEntity)
        {
            return new TileEntityModuleTank(this, tileEntity);
        }
    }
}
