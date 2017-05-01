package cubex2.cs4.plugins.vanilla.gui;

import cubex2.cs4.plugins.vanilla.ContentGuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.PlayerMainInvWrapper;

import java.util.Optional;

public class ContainerGui extends Container
{
    private final ContentGuiContainer content;
    private final ItemHandlerSupplier supplier;
    private final IItemHandler playerInv;

    public ContainerGui(ContentGuiContainer content, ItemHandlerSupplier supplier, EntityPlayer player)
    {
        this.content = content;
        this.supplier = supplier;

        playerInv = new PlayerMainInvWrapper(player.inventory);

        for (SlotData data : content.slots)
        {
            Optional<IItemHandler> inv = getInventory(data.name);

            if (inv.isPresent())
            {
                for (int row = 0; row < data.rows; row++)
                {
                    for (int col = 0; col < data.columns; col++)
                    {
                        int index = data.firstSlot + row * data.columns + col;
                        int x = data.x + col * 18;
                        int y = data.y + row * 18;
                        addSlotToContainer(new SlotItemHandler(inv.get(), index, x, y));
                    }
                }
            }
        }
    }

    private Optional<IItemHandler> getInventory(String name)
    {
        if (name.equals("player"))
            return Optional.of(playerInv);
        else
            return supplier.getItemHandler(name);
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return true;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index)
    {
        ItemStack itemstack = null;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            boolean ruleApplied = false;
            for (ShiftClickRule rule : content.shiftClickRules)
            {
                if (rule.canApply(index))
                {
                    ruleApplied = true;
                    if (!mergeItemStack(itemstack1, rule.getToStart(), rule.getToEnd() + 1, rule.reverseDirection()))
                    {
                        return null;
                    }
                    break;
                }
            }

            if (!ruleApplied)
            {
                return null;
            }

            if (itemstack1.stackSize == 0)
            {
                slot.putStack(null);
            } else
            {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize)
            {
                return null;
            }

            slot.onPickupFromSlot(player, itemstack1);
        }

        return itemstack;
    }
}
