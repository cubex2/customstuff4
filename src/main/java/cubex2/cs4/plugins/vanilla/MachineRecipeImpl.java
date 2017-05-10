package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.api.ContentHelper;
import cubex2.cs4.api.InitPhase;
import cubex2.cs4.api.WrappedItemStack;
import cubex2.cs4.data.SimpleContent;
import cubex2.cs4.plugins.vanilla.crafting.MachineManager;
import cubex2.cs4.plugins.vanilla.crafting.MachineRecipe;
import cubex2.cs4.util.CollectionHelper;
import cubex2.cs4.util.ItemHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.List;

public class MachineRecipeImpl extends SimpleContent implements MachineRecipe
{
    List<WrappedItemStack> input;
    List<WrappedItemStack> output;
    int cookTime = 0;
    ResourceLocation recipeList;

    private transient NonNullList<ItemStack> inputStacks;
    private transient NonNullList<ItemStack> outputStacks;

    @Override
    public boolean matches(NonNullList<ItemStack> input, World world)
    {
        return CollectionHelper.equalsWithoutOrder(input, inputStacks, ItemHelper::isSameStackForMachineInput);
    }

    @Override
    public NonNullList<ItemStack> getResult()
    {
        NonNullList<ItemStack> result = NonNullList.create();
        outputStacks.forEach(stack -> result.add(stack.copy()));
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
        input.forEach(item -> inputStacks.add(item.createItemStack()));

        outputStacks = NonNullList.create();
        output.forEach(item -> outputStacks.add(item.createItemStack()));

        MachineManager.addRecipe(recipeList, this);
    }

    @Override
    protected boolean isReady()
    {
        return input.stream().allMatch(WrappedItemStack::isItemLoaded) &&
               output.stream().allMatch(WrappedItemStack::isItemLoaded);
    }
}
