package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.CustomStuff4;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public abstract class ContentItemWithSubtypes<T extends Item> extends ContentItemBase<T>
{
    public int[] subtypes = new int[0];

    public Attribute<String> creativeTab = Attribute.constant("anonexistingtabtoreturnnull");
    public Attribute<Integer> maxStack = Attribute.constant(64);
    public Attribute<String[]> information = Attribute.constant(new String[0]);

    Attribute<ResourceLocation> model = Attribute.constant(null);

    @Override
    protected void initItem()
    {
        item.setHasSubtypes(subtypes.length > 0);

        if (subtypes.length == 0)
            subtypes = new int[] {0};

        for (int meta : subtypes)
        {
            ResourceLocation model = this.model.get(meta).orElse(item.getRegistryName());
            CustomStuff4.proxy.registerItemModel(item, meta, model);
        }
    }
}
