package cubex2.cs4.plugins.vanilla.gui;

import net.minecraft.item.ItemStack;

public interface ItemFilter
{
    boolean accepts(ItemStack stack);

    ItemFilter EVERYTHING = stack -> true;
}
