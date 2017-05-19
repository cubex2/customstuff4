package cubex2.cs4.api;

public class OreClass
{
    private final String oreName;
    private final int amount;

    public OreClass(String oreName, int amount)
    {
        this.oreName = oreName;
        this.amount = amount;
    }

    public String getOreName()
    {
        return oreName;
    }

    public int getAmount()
    {
        return amount;
    }
}
