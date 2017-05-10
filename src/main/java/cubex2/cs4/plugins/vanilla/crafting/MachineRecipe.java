package cubex2.cs4.plugins.vanilla.crafting;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public interface MachineRecipe
{
    /**
     * Checks if the given items match this recipe. The list is guaranteed to have the size returned by getInputStacks.
     */
    boolean matches(NonNullList<ItemStack> input, World world);

    /**
     * Gets the result items. The returned stacks are put into the machine, so they should be copies of the internal stacks.
     * If the result depends on chance, add an empty stack instead of not adding it.
     */
    NonNullList<ItemStack> getResult();

    /**
     * Gets the number of input stacks
     */
    int getInputStacks();

    /**
     * Get the amount of ticks that this recipe takes to finish. Return 0 to use the default cook time of the machine.
     */
    default int getCookTime()
    {
        return 0;
    }

    MachineRecipe EMPTY = new EmptyRecipe();
}
