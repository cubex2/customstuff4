package cubex2.cs4.compat.jei;

import cubex2.cs4.api.RecipeInput;
import cubex2.cs4.plugins.vanilla.crafting.MachineRecipe;
import cubex2.cs4.plugins.vanilla.crafting.MachineRecipeOutput;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

public class DelegatedMachineRecipe implements MachineRecipe
{
    MachineRecipe recipe;
    MachineRecipeOutput usedOutput;

    @Override
    public boolean matches(NonNullList<ItemStack> input, List<FluidStack> inputFluid, World world)
    {
        return recipe.matches(input, inputFluid, world);
    }

    @Override
    public List<RecipeInput> getRecipeInput()
    {
        return recipe.getRecipeInput();
    }

    @Override
    public List<FluidStack> getFluidRecipeInput()
    {
        return recipe.getFluidRecipeInput();
    }

    @Override
    public NonNullList<MachineRecipeOutput> getOutputs()
    {
        return recipe.getOutputs();
    }

    @Override
    public int getInputStacks()
    {
        return recipe.getInputStacks();
    }

    @Override
    public int getFluidStacks()
    {
        return recipe.getFluidStacks();
    }
}
