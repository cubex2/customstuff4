package cubex2.cs4.plugins.vanilla.crafting;

import cubex2.cs4.api.RecipeInput;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

import java.util.List;

public class EmptyRecipe implements MachineRecipe
{
    private final NonNullList<ItemStack> result = NonNullList.create();

    @Override
    public boolean matches(NonNullList<ItemStack> input, World world)
    {
        return false;
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
