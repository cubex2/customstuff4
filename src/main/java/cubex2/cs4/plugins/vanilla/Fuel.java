package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.api.ContentHelper;
import cubex2.cs4.api.InitPhase;
import cubex2.cs4.api.WrappedItemStack;
import cubex2.cs4.data.SimpleContent;
import cubex2.cs4.util.ItemHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.IFuelHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;

class Fuel extends SimpleContent implements IFuelHandler
{
    WrappedItemStack item;
    int burnTime;

    private transient ItemStack fuelStack;

    @Override
    public int getBurnTime(ItemStack fuel)
    {
        return ItemHelper.isSameStackForFuel(fuel, fuelStack)
               ? burnTime
               : 0;
    }

    @Override
    protected void doInit(InitPhase phase, ContentHelper helper)
    {
        fuelStack = item.getItemStack();
        GameRegistry.registerFuelHandler(this);
    }

    @Override
    protected boolean isReady()
    {
        return item.isItemLoaded();
    }
}
