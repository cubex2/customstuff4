package cubex2.cs4.plugins.vanilla.crafting;

import cubex2.cs4.api.SlotProvider;
import cubex2.cs4.plugins.vanilla.tileentity.ItemHandlerTileEntity;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;
import java.util.Optional;

public class ItemHandlerCrafting extends ItemHandlerTileEntity implements SlotProvider
{
    private final int rows;
    private final int columns;
    private World world;

    public ItemHandlerCrafting(TileEntity tile, int rows, int columns)
    {
        super(rows * columns + 1, tile);
        this.rows = rows;
        this.columns = columns;
    }

    public void setWorld(World world)
    {
        this.world = world;
    }

    public int getWidth()
    {
        return columns;
    }

    public int getHeight()
    {
        return rows;
    }

    @Nonnull
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate)
    {
        if (slot >= rows * columns)
            return stack;

        return super.insertItem(slot, stack, simulate);
    }

    @Nonnull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate)
    {
        if (slot == rows * columns)
        {
            ItemStack result = getCraftResult();
            if (result.isEmpty() || amount < result.getCount())
                return ItemStack.EMPTY;

            amount = result.getCount();

            if (!simulate)
            {
                removeItems();

                setStackInSlot(rows * columns, ItemStack.EMPTY);
                return ItemHandlerHelper.copyStackWithSize(result, amount);
            }
        }

        return super.extractItem(slot, amount, simulate);
    }

    void removeItems()
    {
        for (int i = 0; i < rows * columns; i++)
        {
            extractItem(i, 1, false);
        }
    }

    @Override
    protected void onContentsChanged(int slot)
    {
        setStackInSlot(rows * columns, getCraftResult());

        tile.markDirty();
    }

    private ItemStack getCraftResult()
    {
        InventoryCraftingWrapper wrapper = new InventoryCraftingWrapper(this);
        return CraftingManager.getInstance().findMatchingRecipe(wrapper, world);
    }

    @Override
    public Optional<Slot> createSlot(int index, int x, int y)
    {
        if (index == rows * columns)
            return Optional.of(new SlotItemHandlerCrafting(this, index, x, y));
        else
            return Optional.empty();
    }
}
