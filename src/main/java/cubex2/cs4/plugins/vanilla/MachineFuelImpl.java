package cubex2.cs4.plugins.vanilla;

import com.google.common.collect.Lists;
import cubex2.cs4.api.ContentHelper;
import cubex2.cs4.api.InitPhase;
import cubex2.cs4.api.RecipeInput;
import cubex2.cs4.data.SimpleContent;
import cubex2.cs4.plugins.vanilla.crafting.MachineFuel;
import cubex2.cs4.plugins.vanilla.crafting.MachineManager;
import cubex2.cs4.util.CollectionHelper;
import cubex2.cs4.util.ItemHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.List;

class MachineFuelImpl extends SimpleContent implements MachineFuel
{
    List<RecipeInput> items;
    int burnTime;
    ResourceLocation fuelList;

    private transient List<Object> fuelStacks;

    @Override
    public int getBurnTime(List<ItemStack> items)
    {
        return CollectionHelper.equalsWithoutOrder(items, fuelStacks, ItemHelper::stackMatchesStackOrOreClass)
               ? burnTime
               : 0;
    }

    @Override
    public List<RecipeInput> getFuelInput()
    {
        return items;
    }

    @Override
    protected void doInit(InitPhase phase, ContentHelper helper)
    {
        fuelStacks = Lists.newArrayList();
        for (RecipeInput item : items)
        {
            fuelStacks.add(item.isItemStack() ? item.getStack().createItemStack() : item.getOreClass());
        }

        MachineManager.addFuel(fuelList, this);
    }

    @Override
    protected boolean isReady()
    {
        return items.stream().allMatch(input -> input.isOreClass() || (input.isItemStack() && input.getStack().isItemLoaded()));
    }
}
