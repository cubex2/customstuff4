package cubex2.cs4.plugins.vanilla.crafting;

import com.google.common.collect.Lists;
import cubex2.cs4.api.ContentHelper;
import cubex2.cs4.api.InitPhase;
import cubex2.cs4.api.WrappedFluidStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class MachineRecipeOutputImpl implements MachineRecipeOutput
{
    private static final Random random = new Random();

    private final List<MachineResult> items;
    private final List<WrappedFluidStack> fluids;

    private final NonNullList<ItemStack> outputItems = NonNullList.create();
    private final List<FluidStack> outputFluids = Lists.newArrayList();
    private final int weight;

    public MachineRecipeOutputImpl(List<MachineResult> items, List<WrappedFluidStack> fluids, int weight)
    {
        this.items = items;
        this.fluids = fluids;
        this.weight = weight;
    }

    @Override
    public NonNullList<ItemStack> getOutputItems()
    {
        return outputItems;
    }

    @Override
    public NonNullList<ItemStack> getResultItems()
    {
        NonNullList<ItemStack> result = NonNullList.create();

        for (int i = 0; i < outputItems.size(); i++)
        {
            if (random.nextFloat() < items.get(i).chance)
            {
                result.add(outputItems.get(i).copy());
            } else
            {
                result.add(ItemStack.EMPTY);
            }
        }

        return result;
    }

    @Override
    public List<FluidStack> getOutputFluids()
    {
        return outputFluids;
    }

    @Override
    public List<FluidStack> getResultFluids()
    {
        return outputFluids.stream()
                           .map(FluidStack::copy)
                           .collect(Collectors.toList());
    }

    @Override
    public int getWeight()
    {
        return weight;
    }

    public void doInit(InitPhase phase, ContentHelper helper)
    {
        items.forEach(item -> outputItems.add(item.item.getItemStack()));
        fluids.forEach(fluid -> outputFluids.add(fluid.getFluidStack()));
    }

    public boolean isReady()
    {
        boolean itemsValid = items.stream().allMatch(result -> result.item.isItemLoaded());
        boolean fluidsValid = fluids.stream().allMatch(fluid -> fluid.getFluidStack() != null);

        return itemsValid && fluidsValid;
    }
}
