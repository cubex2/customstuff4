package cubex2.cs4.api;

import net.minecraft.item.ItemStack;

public interface ItemModuleSupplier
{
    ItemModule createModule(ItemStack stack);
}
