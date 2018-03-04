package cubex2.cs4.plugins.vanilla.gui;

import net.minecraftforge.fluids.IFluidTank;

import javax.annotation.Nullable;

public interface FluidSource
{
    /**
     * Get the fluid tank for the given name. Return null if not supported.
     */
    @Nullable
    IFluidTank getFluidTank(String name);

    FluidSource EMPTY = name -> null;
}
