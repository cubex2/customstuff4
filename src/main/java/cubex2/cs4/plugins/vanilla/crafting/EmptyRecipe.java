package cubex2.cs4.plugins.vanilla.crafting;

import com.google.common.collect.Lists;
import cubex2.cs4.api.RecipeInput;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

public class EmptyRecipe implements MachineRecipe
{
    private final List<ItemStack> result = Lists.newArrayList();

    @Override
    public boolean matches(List<ItemStack> input, World world)
    {
        return false;
    }

    @Override
    public List<RecipeInput> getRecipeInput()
    {
        return Lists.newArrayList();
    }

    @Override
    public List<ItemStack> getResult()
    {
        return result;
    }

    @Override
    public List<ItemStack> getRecipeOutput()
    {
        return result;
    }

    @Override
    public int getInputStacks()
    {
        return 0;
    }
}
