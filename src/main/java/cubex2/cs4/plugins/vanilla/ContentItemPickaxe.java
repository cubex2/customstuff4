package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.plugins.vanilla.item.ItemPickaxe;

public class ContentItemPickaxe extends ContentItemTool<ItemPickaxe>
{
    @Override
    protected ItemPickaxe createItem()
    {
        return new ItemPickaxe(material.getToolMaterial(), this);
    }
}
