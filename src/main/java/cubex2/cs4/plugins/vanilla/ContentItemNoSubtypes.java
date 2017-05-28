package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.CustomStuff4;
import cubex2.cs4.util.ItemHelper;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public abstract class ContentItemNoSubtypes<T extends Item> extends ContentItemBase<T>
{
    public String[] information = new String[0];
    String creativeTab = "search";

    ResourceLocation model = null;

    @Override
    protected void initItem()
    {
        item.setCreativeTab(ItemHelper.findCreativeTab(creativeTab).orElse(null));

        if (model == null)
            model = item.getRegistryName();

        CustomStuff4.proxy.registerItemModel(item, 0, model);
    }
}
