package cubex2.cs4.plugins.vanilla;

import com.google.common.collect.Lists;
import cubex2.cs4.api.ContentHelper;
import cubex2.cs4.api.InitPhase;
import cubex2.cs4.api.RecipeInput;
import cubex2.cs4.api.WrappedFluidStack;
import cubex2.cs4.data.SimpleContent;
import cubex2.cs4.plugins.vanilla.crafting.MachineManager;
import cubex2.cs4.plugins.vanilla.crafting.MachineRecipe;
import cubex2.cs4.plugins.vanilla.crafting.MachineRecipeOutput;
import cubex2.cs4.plugins.vanilla.crafting.MachineRecipeOutputImpl;
import cubex2.cs4.util.CollectionHelper;
import cubex2.cs4.util.ItemHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

import java.util.Collections;
import java.util.List;

public class MachineRecipeImpl extends SimpleContent implements MachineRecipe
{
    List<RecipeInput> input = Collections.emptyList();
    List<WrappedFluidStack> inputFluid = Collections.emptyList();
    List<MachineRecipeOutputImpl> output = Collections.emptyList();
    int cookTime = 0;
    ResourceLocation recipeList;

    private final transient List<FluidStack> inputFluidStacks = Lists.newArrayList();
    private final transient NonNullList<MachineRecipeOutput> outputs = NonNullList.create();

    @Override
    public boolean matches(NonNullList<ItemStack> input, List<FluidStack> inputFluid, World world)
    {
        // isSameStackForMachineInput is not transitive, so having a stack as well as its ore class in the input
        // will cause the recipe to not accept the items even if it should.
        return CollectionHelper.equalsWithoutOrder(input, this.input, (t, i) -> ItemHelper.stackMatchesRecipeInput(t, i, true))
               && CollectionHelper.equalsWithoutOrder(inputFluid, this.inputFluidStacks, (t, i) -> ItemHelper.fluidStackEqual(t, i, true));
    }

    @Override
    public NonNullList<MachineRecipeOutput> getOutputs()
    {
        return outputs;
    }

    @Override
    public List<RecipeInput> getRecipeInput()
    {
        return input;
    }

    @Override
    public List<FluidStack> getFluidRecipeInput()
    {
        return inputFluidStacks;
    }

    @Override
    public int getInputStacks()
    {
        return input.size();
    }

    @Override
    public int getFluidStacks()
    {
        return inputFluid.size();
    }

    @Override
    public int getCookTime()
    {
        return cookTime;
    }

    @Override
    protected void doInit(InitPhase phase, ContentHelper helper)
    {
        inputFluid.forEach(fluid -> inputFluidStacks.add(fluid.getFluidStack()));

        for (MachineRecipeOutputImpl recipeOutput : output)
        {
            recipeOutput.doInit(phase, helper);
            outputs.add(recipeOutput);
        }

        MachineManager.addRecipe(recipeList, this);
    }

    @Override
    protected boolean isReady()
    {
        boolean inputItemsValid = input.stream().allMatch(input -> input.isOreClass() || (input.isItemStack() && input.getStack().isItemLoaded()));
        boolean inputFluidValid = inputFluid.stream().allMatch(fluid -> fluid.getFluidStack() != null);
        boolean outputValid = output.stream().allMatch(MachineRecipeOutputImpl::isReady);

        return inputItemsValid &&
               inputFluidValid &&
               outputValid;
    }
}
