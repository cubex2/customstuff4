package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.api.WrappedItemStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

class WrappedItemStackImpl implements WrappedItemStack
{
    public ResourceLocation item;
    public int metadata = 0;
    public int amount = 1;
    public NBTTagCompound nbt;

    public WrappedItemStackImpl(ResourceLocation item, int metadata, int amount)
    {
        this.item = item;
        this.metadata = metadata;
        this.amount = amount;
    }

    public WrappedItemStackImpl()
    {
    }

    @Override
    public ItemStack getItemStack()
    {
        Item object = Item.REGISTRY.getObject(item);
        if (object == null)
        {
            return null;
        } else
        {
            ItemStack stack = new ItemStack(object, amount, metadata);
            if (nbt != null)
            {
                stack.setTagCompound(nbt.copy());
            }
            return stack;
        }
    }

    @Override
    public boolean isItemLoaded()
    {
        Item object = Item.REGISTRY.getObject(item);

        return object != null;
    }
}
