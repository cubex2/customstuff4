package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.CustomStuff4;
import cubex2.cs4.plugins.vanilla.item.ItemSimple;
import net.minecraft.util.ResourceLocation;

import java.util.Arrays;

public class ContentItemSimple extends ContentItemBase<ItemSimple>
{
    public int[] subtypes = new int[0];
    public MetadataAttribute<String> creativeTab = MetadataAttribute.constant("anonexistingtabtoreturnnull");
    MetadataAttribute<ResourceLocation> model = MetadataAttribute.constant(new ResourceLocation("minecraft:stick"));

    @Override
    protected void initItem()
    {
        item.setHasSubtypes(subtypes.length > 0);

        if (subtypes.length == 0)
            subtypes = new int[] {0};

        Arrays.stream(subtypes).forEach(meta -> model.get(meta).ifPresent(model -> CustomStuff4.proxy.registerItemModel(item, meta, model)));
    }

    @Override
    protected ItemSimple createItem()
    {
        return new ItemSimple(this);
    }
}
