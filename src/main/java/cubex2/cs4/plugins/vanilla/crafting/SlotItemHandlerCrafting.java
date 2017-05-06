package cubex2.cs4.plugins.vanilla.crafting;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class SlotItemHandlerCrafting extends SlotItemHandler
{
    public static boolean shiftClick = false;

    private final ItemHandlerCrafting itemHandler;

    public SlotItemHandlerCrafting(ItemHandlerCrafting itemHandler, int index, int xPosition, int yPosition)
    {
        super(itemHandler, index, xPosition, yPosition);
        this.itemHandler = itemHandler;
    }

    @Override
    public boolean canTakeStack(EntityPlayer playerIn)
    {
        boolean result = !getItemHandler().extractItem(getSlotIndex(), Integer.MAX_VALUE, true).isEmpty();
        System.out.println(result);
        return result;
    }

    @Override
    public ItemStack onTake(EntityPlayer thePlayer, ItemStack stack)
    {
        itemHandler.onContentsChanged(getSlotIndex());

        if (shiftClick)
        {
            itemHandler.removeItems();
        }

        return super.onTake(thePlayer, stack);
    }
}
