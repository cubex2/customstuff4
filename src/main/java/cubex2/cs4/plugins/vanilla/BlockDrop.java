package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.api.WrappedItemStack;
import cubex2.cs4.util.IntRange;

public class BlockDrop
{
    private WrappedItemStack item;
    private IntRange amount;
    private IntRange fortuneAmount;

    public BlockDrop(WrappedItemStack item, IntRange amount, IntRange fortuneAmount)
    {
        this.item = item;
        this.amount = amount;
        this.fortuneAmount = fortuneAmount;
    }

    public BlockDrop(WrappedItemStack item, IntRange amount)
    {
        this(item, amount, IntRange.ZERO);
    }

    public BlockDrop()
    {
    }

    public WrappedItemStack getItem()
    {
        return item;
    }

    public int getAmount(int fortune)
    {
        return amount.getRandomValue() + fortune * fortuneAmount.getRandomValue();
    }

    int getMinAmount()
    {
        return amount.getMin();
    }

    int getMaxAmount()
    {
        return amount.getMax();
    }

    IntRange getFortuneAmount()
    {
        return fortuneAmount;
    }
}
