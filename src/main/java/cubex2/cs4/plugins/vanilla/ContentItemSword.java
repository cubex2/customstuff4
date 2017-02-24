package cubex2.cs4.plugins.vanilla;


import cubex2.cs4.plugins.vanilla.item.ItemSword;

public class ContentItemSword extends ContentItemTool<ItemSword>
{
    public ContentItemSword()
    {
        creativeTab = "combat";
    }

    @Override
    protected ItemSword createItem()
    {
        return new ItemSword(material.getToolMaterial(), this);
    }
}
