package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.api.ContentHelper;
import cubex2.cs4.api.InitPhase;
import cubex2.cs4.api.RecipeInput;
import cubex2.cs4.data.SimpleContent;
import cubex2.cs4.plugins.vanilla.crafting.MachineManager;
import cubex2.cs4.plugins.vanilla.crafting.MachineRecipe;
import cubex2.cs4.plugins.vanilla.crafting.MachineResult;
import cubex2.cs4.util.CollectionHelper;
import cubex2.cs4.util.ItemHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class MachineRecipeImpl extends SimpleContent implements MachineRecipe
{
    private static final Random random = new Random();

    List<RecipeInput> input;
    List<MachineResult> output;
    int cookTime = 0;
    ResourceLocation recipeList;

    private transient NonNullList<Object> inputStacks;
    private transient NonNullList<ItemStack> outputStacks;

    @Override
    public boolean matches(NonNullList<ItemStack> input, World world)
    {
        // isSameStackForMachineInput is not transitive, so having a stack as well as its ore class in the input
        // will cause the recipe to not accept the items even if it should.
        return CollectionHelper.equalsWithoutOrder(input, inputStacks, ItemHelper::isSameStackForMachineInput);
    }

    @Override
    public NonNullList<ItemStack> getResult()
    {
        NonNullList<ItemStack> result = NonNullList.create();

        for (int i = 0; i < outputStacks.size(); i++)
        {
            if (random.nextFloat() < output.get(i).chance)
            {
                result.add(outputStacks.get(i).copy());
            } else
            {
                result.add(ItemStack.EMPTY);
            }
        }

        return result;
    }

    @Override
    public int getInputStacks()
    {
        return outputStacks.size();
    }

    @Override
    public int getCookTime()
    {
        return cookTime;
    }

    @Override
    protected void doInit(InitPhase phase, ContentHelper helper)
    {
        inputStacks = NonNullList.create();
        input.forEach(item -> inputStacks.add(item.isItemStack() ? item.getStack().createItemStack() : item.getOreClass()));

        outputStacks = NonNullList.create();
        output.forEach(item -> outputStacks.add(item.item.createItemStack()));

        MachineManager.addRecipe(recipeList, this);
    }

    @Override
    protected boolean isReady()
    {
        return input.stream().allMatch(input -> input.isOreClass() || (input.isItemStack() && input.getStack().isItemLoaded())) &&
               output.stream().allMatch(result -> result.item.isItemLoaded());
    }
}
