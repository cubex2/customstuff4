package cubex2.cs4.plugins.vanilla;

import com.google.common.collect.Lists;
import cubex2.cs4.api.ContentHelper;
import cubex2.cs4.api.InitPhase;
import cubex2.cs4.api.RecipeInput;
import cubex2.cs4.api.WrappedFluidStack;
import cubex2.cs4.data.SimpleContent;
import cubex2.cs4.plugins.vanilla.crafting.MachineManager;
import cubex2.cs4.plugins.vanilla.crafting.MachineRecipe;
import cubex2.cs4.plugins.vanilla.crafting.MachineResult;
import cubex2.cs4.util.CollectionHelper;
import cubex2.cs4.util.ItemHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class MachineRecipeImpl extends SimpleContent implements MachineRecipe
{
    private static final Random random = new Random();

    List<RecipeInput> input = Collections.emptyList();
    List<WrappedFluidStack> inputFluid = Collections.emptyList();
    List<MachineResult> output = Collections.emptyList();
    List<WrappedFluidStack> outputFluid = Collections.emptyList();
    int cookTime = 0;
    ResourceLocation recipeList;

    private final transient List<ItemStack> outputStacks = Lists.newArrayList();
    private final transient List<FluidStack> outputFluidStacks = Lists.newArrayList();
    private final transient List<FluidStack> inputFluidStacks = Lists.newArrayList();

    @Override
    public boolean matches(List<ItemStack> input, List<FluidStack> inputFluid, World world)
    {
        // isSameStackForMachineInput is not transitive, so having a stack as well as its ore class in the input
        // will cause the recipe to not accept the items even if it should.
        return CollectionHelper.equalsWithoutOrder(input, this.input, (t, i) -> ItemHelper.stackMatchesRecipeInput(t, i, true))
               && CollectionHelper.equalsWithoutOrder(inputFluid, this.inputFluidStacks, (t, i) -> ItemHelper.fluidStackEqual(t, i, true));
    }

    @Override
    public List<RecipeInput> getRecipeInput()
    {
        return input;
    }

    @Override
    public List<ItemStack> getResult()
    {
        List<ItemStack> result = Lists.newArrayList();

        for (int i = 0; i < outputStacks.size(); i++)
        {
            if (random.nextFloat() < output.get(i).chance)
            {
                result.add(outputStacks.get(i).copy());
            } else
            {
                result.add(null);
            }
        }

        return result;
    }

    @Override
    public List<ItemStack> getRecipeOutput()
    {
        return outputStacks;
    }

    @Override
    public List<FluidStack> getFluidRecipeInput()
    {
        return inputFluidStacks;
    }

    @Override
    public List<FluidStack> getFluidResult()
    {
        return outputFluidStacks.stream()
                                .map(FluidStack::copy)
                                .collect(Collectors.toList());
    }

    @Override
    public List<FluidStack> getFluidRecipeOutput()
    {
        return outputFluidStacks;
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
        output.forEach(item -> outputStacks.add(item.item.getItemStack()));
        inputFluid.forEach(fluid -> inputFluidStacks.add(fluid.getFluidStack()));
        outputFluid.forEach(fluid -> outputFluidStacks.add(fluid.getFluidStack()));

        MachineManager.addRecipe(recipeList, this);
    }

    @Override
    protected boolean isReady()
    {
        return input.stream().allMatch(input -> input.isOreClass() || (input.isItemStack() && input.getStack().isItemLoaded())) &&
               output.stream().allMatch(result -> result.item.isItemLoaded())
               && outputFluid.stream().allMatch(fluid -> fluid.getFluidStack() != null)
               && inputFluid.stream().allMatch(fluid -> fluid.getFluidStack() != null);
    }
}
