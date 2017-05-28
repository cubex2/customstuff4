package cubex2.cs4.plugins.vanilla.crafting;

import cubex2.cs4.api.RecipeInput;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

import java.util.Collections;
import java.util.List;

public class EmptyRecipe implements MachineRecipe
{
    private final NonNullList<ItemStack> result = NonNullList.create();

    @Override
    public boolean matches(NonNullList<ItemStack> input, List<FluidStack> inputFluid, World world)
    {
        return false;
    }

    @Override
    public List<FluidStack> getFluidRecipeInput()
    {
        return Collections.emptyList();
    }

    @Override
    public List<FluidStack> getFluidResult()
    {
        return Collections.emptyList();
    }

    @Override
    public List<FluidStack> getFluidRecipeOutput()
    {
        return Collections.emptyList();
    }

    @Override
    public int getFluidStacks()
    {
        return 0;
    }

    @Override
    public List<RecipeInput> getRecipeInput()
    {
        return NonNullList.create();
    }

    @Override
    public NonNullList<ItemStack> getResult()
    {
        return result;
    }

    @Override
    public NonNullList<ItemStack> getRecipeOutput()
    {
        return result;
    }

    @Override
    public int getInputStacks()
    {
        return 0;
    }
}
