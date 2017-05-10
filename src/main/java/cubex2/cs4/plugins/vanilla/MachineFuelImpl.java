package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.api.ContentHelper;
import cubex2.cs4.api.InitPhase;
import cubex2.cs4.api.WrappedItemStack;
import cubex2.cs4.data.SimpleContent;
import cubex2.cs4.plugins.vanilla.crafting.MachineFuel;
import cubex2.cs4.plugins.vanilla.crafting.MachineManager;
import cubex2.cs4.util.CollectionHelper;
import cubex2.cs4.util.ItemHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

import java.util.List;

class MachineFuelImpl extends SimpleContent implements MachineFuel
{
    List<WrappedItemStack> items;
    int burnTime;
    ResourceLocation fuelList;

    private transient NonNullList<ItemStack> fuelStacks;

    @Override
    public int getBurnTime(NonNullList<ItemStack> items)
    {
        return CollectionHelper.equalsWithoutOrder(items, fuelStacks, ItemHelper::isSameStackForFuel)
               ? burnTime
               : 0;
    }

    @Override
    protected void doInit(InitPhase phase, ContentHelper helper)
    {
        fuelStacks = NonNullList.create();
        for (WrappedItemStack item : items)
        {
            fuelStacks.add(item.createItemStack());
        }

        MachineManager.addFuel(fuelList, this);
    }

    @Override
    protected boolean isReady()
    {
        return items.stream().allMatch(WrappedItemStack::isItemLoaded);
    }
}
