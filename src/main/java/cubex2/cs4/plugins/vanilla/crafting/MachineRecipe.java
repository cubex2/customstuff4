package cubex2.cs4.plugins.vanilla.crafting;

import cubex2.cs4.api.RecipeInput;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

public interface MachineRecipe
{
    /**
     * Checks if the given items and fluids match this recipe. The lists are guaranteed to have the size returned by getInputStacks and getInputFluids.
     */
    boolean matches(NonNullList<ItemStack> input, List<FluidStack> inputFluid, World world);

    List<RecipeInput> getRecipeInput();

    List<FluidStack> getFluidRecipeInput();

    /**
     * Get all possible outputs for this recipe. The output that is being used is randomly chosen according to its weight.
     */
    NonNullList<MachineRecipeOutput> getOutputs();

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
