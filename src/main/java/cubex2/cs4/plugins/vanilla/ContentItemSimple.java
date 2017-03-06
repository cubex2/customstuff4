package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.plugins.vanilla.item.ItemSimple;

public class ContentItemSimple extends ContentItemWithSubtypes<ItemSimple>
{
    @Override
    protected ItemSimple createItem()
    {
        return new ItemSimple(this);
    }
}
