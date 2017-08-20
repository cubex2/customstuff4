package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.CustomStuff4;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public abstract class ContentBlockBaseNoSubtypes extends ContentBlockBase
{
    @Override
    protected void registerModels()
    {
        if (item != null)
        {
            ResourceLocation model = itemModel.get(0).orElse(item.getRegistryName());
            CustomStuff4.proxy.registerItemModel(item, 0, model);
        }
    }
}
