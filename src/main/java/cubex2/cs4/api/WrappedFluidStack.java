package cubex2.cs4.api;

import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;

/**
 * Contains the data to construct a FluidStack. Necessary as deserialization happens during preInit while fluids can be
 * registered later.
 */
public interface WrappedFluidStack
{
    /**
     * Tries to create the FluidStack. This returns null if the item for the stack does not exist or hasn't been loaded
     * yet. The returned stack must not be modified.
     */
    @Nullable
    FluidStack getFluidStack();
}
