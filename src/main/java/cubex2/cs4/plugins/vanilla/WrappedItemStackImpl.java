package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.api.WrappedItemStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

class WrappedItemStackImpl implements WrappedItemStack
{
    public String item;
    public int metadata = 0;
    public int amount = 1;

    @Nullable
    @Override
    public ItemStack createItemStack()
    {
        Item object = Item.REGISTRY.getObject(new ResourceLocation(item));
        if (object == null)
        {
            return null;
        } else
        {
            return new ItemStack(object, amount, metadata);
        }
    }
}
