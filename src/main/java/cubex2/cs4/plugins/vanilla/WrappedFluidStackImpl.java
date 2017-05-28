package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.api.WrappedFluidStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;

class WrappedFluidStackImpl implements WrappedFluidStack
{
    public String fluid;
    public int amount = 1000;

    public WrappedFluidStackImpl(String fluid, int amount)
    {
        this.fluid = fluid;
        this.amount = amount;
    }

    public WrappedFluidStackImpl()
    {
    }

    @Nullable
    @Override
    public FluidStack getFluidStack()
    {
        return FluidRegistry.getFluidStack(fluid, amount);
    }
}
