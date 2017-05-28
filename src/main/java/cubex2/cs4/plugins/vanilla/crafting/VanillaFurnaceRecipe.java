package cubex2.cs4.plugins.vanilla.crafting;

import cubex2.cs4.api.RecipeInput;
import cubex2.cs4.plugins.vanilla.RecipeInputImpl;
import cubex2.cs4.plugins.vanilla.WrappedItemStackConstant;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

import java.util.Collections;
import java.util.List;

public class VanillaFurnaceRecipe implements MachineRecipe
{
    private final ItemStack result;
    private final List<RecipeInput> resultList;

    public VanillaFurnaceRecipe(ItemStack result)
    {
        this.result = result;
        resultList = Collections.singletonList(new RecipeInputImpl(new WrappedItemStackConstant(result)));
    }

    @Override
    public boolean matches(List<ItemStack> input, List<FluidStack> inputFluid, World world)
    {
        return compareItemStacks(FurnaceRecipes.instance().getSmeltingResult(input.get(0)), result);
    }

    private boolean compareItemStacks(ItemStack stack1, ItemStack stack2)
    {
        return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
    }

    @Override
    public List<RecipeInput> getRecipeInput()
    {
        return resultList;
    }

    @Override
    public List<ItemStack> getResult()
    {
        return Collections.singletonList(result.copy());
    }

    @Override
    public List<ItemStack> getRecipeOutput()
    {
        return Collections.singletonList(result);
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
    public int getInputStacks()
    {
        return 1;
    }
}
