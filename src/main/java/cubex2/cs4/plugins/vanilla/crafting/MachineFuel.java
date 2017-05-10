package cubex2.cs4.plugins.vanilla.crafting;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public interface MachineFuel
{
    /**
     * Return the burn time for the given items. If the list contains all needed items but additional items as well,
     * this should still return 0.
     */
    int getBurnTime(NonNullList<ItemStack> items);
}
