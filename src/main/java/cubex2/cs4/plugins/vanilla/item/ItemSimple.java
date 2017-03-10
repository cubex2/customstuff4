package cubex2.cs4.plugins.vanilla.item;

import cubex2.cs4.plugins.vanilla.ContentItemSimple;
import cubex2.cs4.plugins.vanilla.ContentItemWithSubtypes;
import net.minecraft.item.Item;

public class ItemSimple extends Item implements ItemWithSubtypes
{
    private final ContentItemSimple content;

    public ItemSimple(ContentItemSimple content)
    {
        this.content = content;
    }

    @Override
    public ContentItemWithSubtypes<?> getContent()
    {
        return content;
    }
}
