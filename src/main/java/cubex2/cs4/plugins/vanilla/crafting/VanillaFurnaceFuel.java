package cubex2.cs4.plugins.vanilla.crafting;

import cubex2.cs4.api.RecipeInput;
import cubex2.cs4.plugins.vanilla.RecipeInputImpl;
import cubex2.cs4.plugins.vanilla.WrappedItemStackConstant;
import cubex2.cs4.util.ItemHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.Collections;
import java.util.List;

public class VanillaFurnaceFuel implements MachineFuel
{
    private final List<RecipeInput> input;
    private final int burnTime;

    public VanillaFurnaceFuel(ItemStack fuel, int burnTime)
    {
        input = Collections.singletonList(new RecipeInputImpl(new WrappedItemStackConstant(fuel)));
        this.burnTime = burnTime;
    }

    @Override
    public int getBurnTime()
    {
        return burnTime;
    }

    @Override
    public boolean matches(NonNullList<ItemStack> items)
    {
        return ItemHelper.stackMatchesRecipeInput(items.get(0), input.get(0), true);
    }

    @Override
    public List<RecipeInput> getFuelInput()
    {
        return input;
    }
}
