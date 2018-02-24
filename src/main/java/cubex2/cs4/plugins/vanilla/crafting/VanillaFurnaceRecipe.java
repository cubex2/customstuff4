package cubex2.cs4.plugins.vanilla.crafting;

import cubex2.cs4.api.RecipeInput;
import cubex2.cs4.plugins.vanilla.RecipeInputImpl;
import cubex2.cs4.plugins.vanilla.WrappedItemStackConstant;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

import java.util.Collections;
import java.util.List;

public class VanillaFurnaceRecipe implements MachineRecipe
{
    private final ItemStack input;
    private final ItemStack result;
    private final NonNullList<MachineRecipeOutput> outputs;
    private final List<RecipeInput> inputList;

    public VanillaFurnaceRecipe(ItemStack input)
    {
        this.input = input;
        this.result = FurnaceRecipes.instance().getSmeltingResult(input);
        outputs = NonNullList.withSize(1, new SimpleMachineRecipeOutput(result));
        inputList = Collections.singletonList(new RecipeInputImpl(new WrappedItemStackConstant(input)));
    }

    @Override
    public boolean matches(NonNullList<ItemStack> input, List<FluidStack> inputFluid, World world)
    {
        return compareItemStacks(input.get(0), this.input);
    }

    private boolean compareItemStacks(ItemStack stack1, ItemStack stack2)
    {
        return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
    }

    @Override
    public List<RecipeInput> getRecipeInput()
    {
        return inputList;
    }

    @Override
    public NonNullList<MachineRecipeOutput> getOutputs()
    {
        return outputs;
    }

    @Override
    public List<FluidStack> getFluidRecipeInput()
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
