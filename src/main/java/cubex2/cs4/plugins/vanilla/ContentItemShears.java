package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.plugins.vanilla.item.ItemShears;

public class ContentItemShears extends ContentItemNoSubtypes<ItemShears>
{
    Integer durability = null;

    public ContentItemShears()
    {
        creativeTab = "tools";
    }

    @Override
    protected ItemShears createItem()
    {
        return new ItemShears(this);
    }

    @Override
    protected void initItem()
    {
        if (durability != null)
            item.setMaxDamage(durability);

        super.initItem();
    }
}
