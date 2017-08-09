package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.api.WrappedItemStack;
import cubex2.cs4.util.IntRange;

public class BlockDrop
{
    private WrappedItemStack item;
    private IntRange amount;

    public BlockDrop(WrappedItemStack item, IntRange amount)
    {
        this.item = item;
        this.amount = amount;
    }

    public BlockDrop()
    {
    }

    public WrappedItemStack getItem()
    {
        return item;
    }

    public int getAmount()
    {
        return amount.getRandomValue();
    }

    int getMinAmount()
    {
        return amount.getMin();
    }

    int getMaxAmount()
    {
        return amount.getMax();
    }
}
