package cubex2.cs4.plugins.vanilla;

import com.google.common.collect.Lists;
import cubex2.cs4.api.ContentHelper;
import cubex2.cs4.api.InitPhase;
import cubex2.cs4.api.WrappedItemStack;
import cubex2.cs4.data.SimpleContent;
import cubex2.cs4.util.ItemHelper;
import net.minecraft.item.ItemStack;

import java.util.List;

class Fuel extends SimpleContent
{
    static final List<Fuel> INSTANCES = Lists.newArrayList();

    WrappedItemStack item;
    int burnTime;

    private transient ItemStack fuelStack;

    /**
     * Checks if the given stack's burn time is changed by this fuel.
     */
    boolean appliesToStack(ItemStack fuel)
    {
        return ItemHelper.isSameStackForFuel(fuel, fuelStack);
    }

    @Override
    protected void doInit(InitPhase phase, ContentHelper helper)
    {
        fuelStack = item.getItemStack();
        INSTANCES.add(this);
    }

    @Override
    protected boolean isReady()
    {
        return item.isItemLoaded();
    }
}
