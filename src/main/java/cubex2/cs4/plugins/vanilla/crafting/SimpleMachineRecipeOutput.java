package cubex2.cs4.plugins.vanilla.crafting;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.FluidStack;

import java.util.Collections;
import java.util.List;

public class SimpleMachineRecipeOutput implements MachineRecipeOutput
{
    private final ItemStack result;
    private final NonNullList<ItemStack> resultList;

    public SimpleMachineRecipeOutput(ItemStack result)
    {
        this.result = result;
        resultList = NonNullList.withSize(1, result);
    }

    @Override
    public NonNullList<ItemStack> getOutputItems()
    {
        return resultList;
    }

    @Override
    public NonNullList<ItemStack> getResultItems()
    {
        return NonNullList.withSize(1, result.copy());
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
        return 1;
    }
}
