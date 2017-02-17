package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.api.ContentHelper;
import cubex2.cs4.api.InitPhase;
import cubex2.cs4.api.WrappedItemStack;
import cubex2.cs4.data.SimpleContent;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.IFuelHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

class Fuel extends SimpleContent implements IFuelHandler
{
    WrappedItemStack item;
    int burnTime;

    private transient ItemStack fuelStack;

    @Override
    public int getBurnTime(ItemStack fuel)
    {
        boolean itemEqual = fuelStack.getMetadata() == OreDictionary.WILDCARD_VALUE
                            ? fuel.isItemEqualIgnoreDurability(fuelStack)
                            : fuel.isItemEqual(fuelStack);

        boolean nbtEqual = !fuelStack.hasTagCompound() || ItemStack.areItemStackTagsEqual(fuelStack, fuel);

        return itemEqual && nbtEqual
               ? burnTime
               : 0;
    }

    @Override
    protected void doInit(InitPhase phase, ContentHelper helper)
    {
        fuelStack = item.createItemStack();
        GameRegistry.registerFuelHandler(this);
    }

    @Override
    protected boolean isReady()
    {
        return item.isItemLoaded();
    }
}
