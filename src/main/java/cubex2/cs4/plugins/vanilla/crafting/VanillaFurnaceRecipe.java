package cubex2.cs4.plugins.vanilla.crafting;

import com.google.common.collect.Lists;
import cubex2.cs4.api.RecipeInput;
import cubex2.cs4.plugins.vanilla.RecipeInputImpl;
import cubex2.cs4.plugins.vanilla.WrappedItemStackConstant;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.world.World;

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
    public boolean matches(List<ItemStack> input, World world)
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
        return Lists.newArrayList(result.copy());
    }

    @Override
    public List<ItemStack> getRecipeOutput()
    {
        return Lists.newArrayList(result);
    }

    @Override
    public int getInputStacks()
    {
        return 1;
    }
}
