package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.CustomStuff4;
import net.minecraft.item.Item;

public abstract class ContentBlockBaseNoSubtypes extends ContentBlockBase
{
    protected void initItem(Item item)
    {
        super.initItem(item);

        itemModel.get(0).ifPresent(model -> CustomStuff4.proxy.registerItemModel(item, 0, model));
    }
}
