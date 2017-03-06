package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.CustomStuff4;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import java.util.Arrays;

public abstract class ContentItemWithSubtypes<T extends Item> extends ContentItemBase<T>
{
    public int[] subtypes = new int[0];

    public MetadataAttribute<String> creativeTab = MetadataAttribute.constant("anonexistingtabtoreturnnull");
    public MetadataAttribute<Integer> maxStack = MetadataAttribute.constant(64);
    public MetadataAttribute<String[]> information = MetadataAttribute.constant(new String[0]);

    MetadataAttribute<ResourceLocation> model = MetadataAttribute.constant(new ResourceLocation("minecraft:stick"));

    @Override
    protected void initItem()
    {
        item.setHasSubtypes(subtypes.length > 0);

        if (subtypes.length == 0)
            subtypes = new int[] {0};

        Arrays.stream(subtypes).forEach(meta -> model.get(meta).ifPresent(model -> CustomStuff4.proxy.registerItemModel(item, meta, model)));
    }
}
