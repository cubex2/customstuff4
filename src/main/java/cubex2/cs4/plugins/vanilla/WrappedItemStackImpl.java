package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.api.WrappedItemStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

class WrappedItemStackImpl implements WrappedItemStack
{
    public ResourceLocation item;
    public int metadata = 0;
    public int amount = 1;

    @Override
    public ItemStack createItemStack()
    {
        Item object = Item.REGISTRY.getObject(item);
        if (object == null)
        {
            return ItemStack.EMPTY;
        } else
        {
            return new ItemStack(object, amount, metadata);
        }
    }
}
