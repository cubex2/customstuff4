package cubex2.cs4.plugins.vanilla.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class SlotItemHandlerReadOnly extends SlotItemHandler
{
    public SlotItemHandlerReadOnly(IItemHandler itemHandler, int index, int xPosition, int yPosition)
    {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(@Nonnull ItemStack stack)
    {
        return false;
    }

    @Override
    public boolean canTakeStack(EntityPlayer playerIn)
    {
        return false;
    }
}
