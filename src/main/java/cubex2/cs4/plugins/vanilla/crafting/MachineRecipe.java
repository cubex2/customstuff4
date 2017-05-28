package cubex2.cs4.plugins.vanilla.crafting;

import cubex2.cs4.api.RecipeInput;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

public interface MachineRecipe
{
    /**
     * Checks if the given items and fluids match this recipe. The lists are guaranteed to have the size returned by getInputStacks and getInputFluids.
     */
    boolean matches(List<ItemStack> input, List<FluidStack> inputFluid, World world);

    List<RecipeInput> getRecipeInput();

    /**
     * Gets the result items. The returned stacks are put into the machine, so they should be copies of the internal stacks.
     * If the result depends on chance, add an empty stack instead of not adding it.
     */
    List<ItemStack> getResult();

    /**
     * Gets the items that this recipe creates. This must not return empty stacks.
     */
    List<ItemStack> getRecipeOutput();

    List<FluidStack> getFluidRecipeInput();

    List<FluidStack> getFluidResult();

    List<FluidStack> getFluidRecipeOutput();

    /**
     * Gets the number of input stacks
     */
    int getInputStacks();

    /**
     * Gets the number of input fluids
     */
    int getFluidStacks();

    /**
     * Get the amount of ticks that this recipe takes to finish. Return 0 to use the default cook time of the machine.
     */
    default int getCookTime()
    {
        return 0;
    }

    MachineRecipe EMPTY = new EmptyRecipe();
}
