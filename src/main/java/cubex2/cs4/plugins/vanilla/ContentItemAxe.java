package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.plugins.vanilla.item.ItemAxe;

public class ContentItemAxe extends ContentItemTool<ItemAxe>
{
    @Override
    protected ItemAxe createItem()
    {
        return new ItemAxe(material.getToolMaterial(), this);
    }
}
