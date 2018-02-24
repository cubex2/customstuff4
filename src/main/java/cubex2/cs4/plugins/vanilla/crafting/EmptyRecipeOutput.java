package cubex2.cs4.plugins.vanilla.crafting;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.FluidStack;

import java.util.Collections;
import java.util.List;

public class EmptyRecipeOutput implements MachineRecipeOutput
{
    private final NonNullList<ItemStack> result = NonNullList.create();

    @Override
    public NonNullList<ItemStack> getOutputItems()
    {
        return result;
    }

    @Override
    public NonNullList<ItemStack> getResultItems()
    {
        return result;
    }

    @Override
    public List<FluidStack> getOutputFluids()
    {
        return Collections.emptyList();
    }

    @Override
    public List<FluidStack> getResultFluids()
    {
        return Collections.emptyList();
    }

    @Override
    public int getWeight()
    {
        return 0;
    }
}
