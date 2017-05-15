package cubex2.cs4.plugins.vanilla.crafting;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;

public class InventoryCraftingWrapper extends InventoryCrafting
{
    private final ItemHandlerCrafting itemHandler;

    public InventoryCraftingWrapper(ItemHandlerCrafting itemHandler)
    {
        super(null, itemHandler.getWidth(), itemHandler.getHeight());

        this.itemHandler = itemHandler;
    }

    @Override
    public ItemStack getStackInSlot(int index)
    {
        return itemHandler.getStackInSlot(index);
    }

    @Override
    public ItemStack removeStackFromSlot(int index)
    {
        ItemStack stack = getStackInSlot(index);
        setInventorySlotContents(index, null);
        return stack;
    }

    @Override
    public ItemStack decrStackSize(int index, int count)
    {
        if (getStackInSlot(index) != null && count > 0)
            return getStackInSlot(index).splitStack(count);

        return null;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack)
    {
        itemHandler.setStackInSlot(index, stack);
    }
}
