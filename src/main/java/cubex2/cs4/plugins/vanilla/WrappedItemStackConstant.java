package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.api.WrappedItemStack;
import net.minecraft.item.ItemStack;

public class WrappedItemStackConstant implements WrappedItemStack
{
    private final ItemStack stack;

    public WrappedItemStackConstant(ItemStack stack) {this.stack = stack;}

    @Override
    public ItemStack getItemStack()
    {
        return stack;
    }

    @Override
    public boolean isItemLoaded()
    {
        return true;
    }
}
