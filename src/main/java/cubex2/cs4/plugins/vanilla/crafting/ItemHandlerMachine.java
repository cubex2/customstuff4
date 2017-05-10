package cubex2.cs4.plugins.vanilla.crafting;

import cubex2.cs4.plugins.vanilla.tileentity.ItemHandlerTileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;

public class ItemHandlerMachine extends ItemHandlerTileEntity
{
    private final int inputSlots;
    private final int outputSlots;
    private final int fuelSlots;

    public ItemHandlerMachine(int inputSlots, int outputSlots, int fuelSlots, TileEntity tile)
    {
        super(inputSlots + outputSlots + fuelSlots, tile);
        this.inputSlots = inputSlots;
        this.outputSlots = outputSlots;
        this.fuelSlots = fuelSlots;
    }

    private boolean isOutputSlot(int index)
    {
        return index >= inputSlots && index < inputSlots + outputSlots;
    }

    private NonNullList<ItemStack> getRange(int from, int to)
    {
        NonNullList<ItemStack> list = NonNullList.create();

        for (int i = from; i <= to; i++)
        {
            if (!getStackInSlot(i).isEmpty())
            {
                list.add(getStackInSlot(i));
            }
        }

        return list;
    }

    public NonNullList<ItemStack> getInputStacks()
    {
        return getRange(0, inputSlots - 1);
    }

    public NonNullList<ItemStack> getFuelStacks()
    {
        return getRange(inputSlots + outputSlots, inputSlots + outputSlots + fuelSlots - 1);
    }

    public ItemStack insertOutput(int outputSlot, ItemStack stack, boolean simulate)
    {
        return super.insertItem(inputSlots + outputSlot, stack, simulate);
    }

    @Nonnull
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate)
    {
        if (isOutputSlot(slot))
            return stack;

        return super.insertItem(slot, stack, simulate);
    }

    public void shrinkFuel()
    {
        for (int i = 0; i < fuelSlots; i++)
        {
            extractFuel(i, 1, false);
        }
    }

    public ItemStack extractFuel(int fuelSlot, int amount, boolean simulate)
    {
        return extractItem(inputSlots + outputSlots + fuelSlot, amount, simulate);
    }

    public void setFuelSlot(int fuelSlot, ItemStack stack)
    {
        setStackInSlot(inputSlots + outputSlots + fuelSlot, stack);
    }

    public void shrinkInput()
    {
        for (int i = 0; i < inputSlots; i++)
        {
            extractInput(i, 1, false);
        }
    }

    public ItemStack extractInput(int inputSlot, int amount, boolean simulate)
    {
        return extractItem(inputSlot, amount, simulate);
    }
}
