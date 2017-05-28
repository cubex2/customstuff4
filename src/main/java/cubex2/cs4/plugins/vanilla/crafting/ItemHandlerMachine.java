package cubex2.cs4.plugins.vanilla.crafting;

import cubex2.cs4.api.RecipeInput;
import cubex2.cs4.plugins.vanilla.tileentity.ItemHandlerTileEntity;
import cubex2.cs4.util.ItemHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.EmptyHandler;
import net.minecraftforge.items.wrapper.RangedWrapper;

import javax.annotation.Nonnull;
import java.util.List;

public class ItemHandlerMachine extends ItemHandlerTileEntity
{
    private final int inputSlots;
    private final int outputSlots;
    private final int fuelSlots;

    private final IItemHandlerModifiable inputHandler;
    private final IItemHandlerModifiable outputHandler;
    private final IItemHandlerModifiable fuelHandler;

    public ItemHandlerMachine(int inputSlots, int outputSlots, int fuelSlots, TileEntity tile)
    {
        super(inputSlots + outputSlots + fuelSlots, tile);
        this.inputSlots = inputSlots;
        this.outputSlots = outputSlots;
        this.fuelSlots = fuelSlots;

        inputHandler = inputSlots > 0 ? new RangedWrapper(this, 0, inputSlots)
                                      : (IItemHandlerModifiable) EmptyHandler.INSTANCE;
        outputHandler = outputSlots > 0 ? new RangedWrapper(this, inputSlots, inputSlots + outputSlots)
                                        : (IItemHandlerModifiable) EmptyHandler.INSTANCE;
        fuelHandler = fuelSlots > 0 ? new RangedWrapper(this, inputSlots + outputSlots, inputSlots + outputSlots + fuelSlots)
                                    : (IItemHandlerModifiable) EmptyHandler.INSTANCE;
    }

    public IItemHandlerModifiable getInputHandler()
    {
        return inputHandler;
    }

    public IItemHandlerModifiable getOutputHandler()
    {
        return outputHandler;
    }

    public IItemHandlerModifiable getFuelHandler()
    {
        return fuelHandler;
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

    public void removeInputsFromInput(List<RecipeInput> inputs)
    {
        ItemHelper.removeInputsFromInventory(inputs, this, 0, inputSlots);
    }

    public void removeInputsFromFuel(List<RecipeInput> inputs)
    {
        ItemHelper.removeInputsFromInventory(inputs, this, inputSlots + outputSlots, fuelSlots);
    }
}
