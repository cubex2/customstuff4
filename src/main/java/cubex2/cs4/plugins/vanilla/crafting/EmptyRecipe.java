package cubex2.cs4.plugins.vanilla.crafting;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class EmptyRecipe implements MachineRecipe
{
    private final NonNullList<ItemStack> result = NonNullList.create();

    @Override
    public boolean matches(NonNullList<ItemStack> input, World world)
    {
        return false;
    }

    @Override
    public NonNullList<ItemStack> getResult()
    {
        return result;
    }

    @Override
    public int getInputStacks()
    {
        return 0;
    }
}
