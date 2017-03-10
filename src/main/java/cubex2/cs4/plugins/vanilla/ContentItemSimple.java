package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.plugins.vanilla.item.ItemFactory;
import net.minecraft.item.Item;

public class ContentItemSimple extends ContentItemWithSubtypes<Item>
{
    @Override
    protected Item createItem()
    {
        return ItemFactory.createSimple(this);
    }
}
