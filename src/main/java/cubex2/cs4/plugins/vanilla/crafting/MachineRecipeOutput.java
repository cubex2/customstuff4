package cubex2.cs4.plugins.vanilla.crafting;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

public interface MachineRecipeOutput
{
    /**
     * Gets the items that this recipe creates. If this returns an empty stack at index i, getResultItems MUST also
     * return an empty stack at index i.
     */
    NonNullList<ItemStack> getOutputItems();

    /**
     * Gets the result items. The returned stacks are put into the machine, so they should be copies of the internal stacks.
     * If the result depends on chance, add an empty stack instead of not adding it. The returned list has to be of the
     * same size as the one returned by getOutputItems.
     */
    NonNullList<ItemStack> getResultItems();

    /**
     * Gets the fluid that this recipe creates. If the fluid at index i is null, getResultFluids MUST also return
     * null at that index.
     */
    List<FluidStack> getOutputFluids();

    /**
     * Gets the result fluids. These are the fluids that are added to the machine, so they should be copies of the internal
     * fluids. The returned list can contain null, which indicates that no fluid at the index is added to the machine. The
     * size of the list has to be the same as the one returned by getOutputFluids.
     */
    List<FluidStack> getResultFluids();

    /**
     * If a recipe has multiple outputs, this weight will be used to determine which output will be used.
     * This should return a value greater than zero.
     */
    int getWeight();

    MachineRecipeOutput EMPTY = new EmptyRecipeOutput();
}
