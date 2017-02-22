package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.CustomStuff4;
import cubex2.cs4.plugins.vanilla.block.BlockSimple;
import cubex2.cs4.plugins.vanilla.block.BlockSimpleWithSubtypes;
import cubex2.cs4.plugins.vanilla.block.ItemBlock;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import java.util.Arrays;
import java.util.Optional;

public class ContentBlockSimple extends ContentBlockBase<BlockSimple>
{
    public int[] subtypes = new int[0];
    public MetadataAttribute<String> creativeTab = MetadataAttribute.constant("anonexistingtabtoreturnnull");
    MetadataAttribute<ResourceLocation> itemModel = MetadataAttribute.constant(new ResourceLocation("minecraft:stick"));

    private transient boolean hasSubtypes;

    @Override
    protected void initBlock()
    {

    }

    @Override
    protected BlockSimple createBlock()
    {
        hasSubtypes = subtypes.length > 0;

        if (subtypes.length == 0)
            subtypes = new int[] {0};

        if (hasSubtypes)
            return new BlockSimpleWithSubtypes(material, this);
        else
            return new BlockSimple(material, this);
    }

    @Override
    protected Optional<Item> createItem()
    {
        ItemBlock item = new ItemBlock(block, this);

        item.setHasSubtypes(hasSubtypes);
        Arrays.stream(subtypes).forEach(meta -> CustomStuff4.proxy.registerItemModel(item, meta, itemModel.get(meta)));

        return Optional.of(item);
    }
}
