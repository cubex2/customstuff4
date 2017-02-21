package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.plugins.vanilla.item.ItemShovel;

public class ContentItemShovel extends ContentItemTool<ItemShovel>
{
    @Override
    protected ItemShovel createItem()
    {
        return new ItemShovel(material.getToolMaterial(), this);
    }
}
