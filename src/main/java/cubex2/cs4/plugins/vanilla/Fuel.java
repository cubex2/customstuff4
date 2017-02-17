package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.api.Content;
import cubex2.cs4.api.ContentHelper;
import cubex2.cs4.api.InitPhase;
import cubex2.cs4.api.WrappedItemStack;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.IFuelHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

class Fuel implements IFuelHandler, Content
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

        return itemEqual && ItemStack.areItemStackTagsEqual(fuelStack, fuel)
               ? burnTime
               : 0;
    }

    private boolean initialized = false;

    @Override
    public void init(InitPhase phase, ContentHelper helper)
    {
        if (!initialized && isReady())
        {
            fuelStack = item.createItemStack();
            GameRegistry.registerFuelHandler(this);

            initialized = true;
        }
    }

    private boolean isReady()
    {
        return item.isItemLoaded();
    }
}
