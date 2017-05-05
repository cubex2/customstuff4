package cubex2.cs4.plugins.vanilla.gui;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import cubex2.cs4.plugins.vanilla.ContentGuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.PlayerMainInvWrapper;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ContainerGui extends Container
{
    private final ContentGuiContainer content;
    private final ItemHandlerSupplier supplier;
    private final IItemHandler playerInv;
    private final Map<SlotData, List<SlotItemHandler>> slotMap = Maps.newHashMap();

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
                        addSlot(data, new SlotItemHandler(inv.get(), index, x, y));
                    }
                }
            }
        }
    }

    private void addSlot(SlotData data, SlotItemHandler slot)
    {
        addSlotToContainer(slot);
        if (!slotMap.containsKey(data))
            slotMap.put(data, Lists.newArrayList());
        slotMap.get(data).add(slot);
    }

    private Optional<IItemHandler> getInventory(String name)
    {
        if (name.equals("player"))
            return Optional.of(playerInv);
        else
            return supplier.getItemHandler(name);
    }

    @Override
    public void onContainerClosed(EntityPlayer playerIn)
    {
        super.onContainerClosed(playerIn);

        if (!playerIn.world.isRemote)
        {
            for (SlotData data : content.slots)
            {
                if (data.dropOnClose)
                {
                    List<SlotItemHandler> slots = slotMap.getOrDefault(data, Collections.emptyList());
                    for (SlotItemHandler slot : slots)
                    {
                        ItemStack stack = slot.decrStackSize(Integer.MAX_VALUE);

                        if (!stack.isEmpty())
                        {
                            playerIn.dropItem(stack, false);
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return true;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index)
    {
        ItemStack itemstack = ItemStack.EMPTY;
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
                        return ItemStack.EMPTY;
                    }
                    break;
                }
            }

            if (!ruleApplied)
            {
                return ItemStack.EMPTY;
            }

            if (itemstack1.getCount() == 0)
            {
                slot.putStack(ItemStack.EMPTY);
            } else
            {
                slot.onSlotChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount())
            {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemstack1);
        }

        return itemstack;
    }
}
