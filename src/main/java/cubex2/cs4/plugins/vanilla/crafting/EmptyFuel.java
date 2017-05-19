package cubex2.cs4.plugins.vanilla.crafting;

import cubex2.cs4.api.RecipeInput;
import net.minecraft.item.ItemStack;

import java.util.Collections;
import java.util.List;

public class EmptyFuel implements MachineFuel
{
    @Override
    public int getBurnTime()
    {
        return 0;
    }

    @Override
    public boolean matches(List<ItemStack> items)
    {
        return false;
    }

    @Override
    public List<RecipeInput> getFuelInput()
    {
        return Collections.emptyList();
    }
}
