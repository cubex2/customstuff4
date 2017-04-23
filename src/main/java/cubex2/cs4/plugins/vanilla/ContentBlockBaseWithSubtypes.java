package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.CustomStuff4;
import cubex2.cs4.util.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.item.Item;

import java.util.Arrays;
import java.util.Optional;

public abstract class ContentBlockBaseWithSubtypes extends ContentBlockBase
{
    private static ContentBlockBaseWithSubtypes activeContent;

    public int[] subtypes = new int[0];
    private transient boolean hasSubtypes;

    @Override
    protected final Block createBlock()
    {
        hasSubtypes = subtypes.length > 0;

        if (subtypes.length == 0)
            subtypes = new int[] {0};

        Block block;
        if (hasSubtypes)
        {
            activeContent = this;
            block = createBlockWithSubtypes();
            activeContent = null;
        } else
        {
            block = createBlockWithoutSubtypes();
        }

        return block;
    }

    protected abstract Block createBlockWithSubtypes();

    protected abstract Block createBlockWithoutSubtypes();

    @Override
    protected final Optional<Item> createItem()
    {
        return createItem(hasSubtypes);
    }

    @Override
    protected void initItem(Item item)
    {
        super.initItem(item);

        item.setHasSubtypes(hasSubtypes);
        Arrays.stream(subtypes).forEach(meta -> itemModel.get(meta).ifPresent(model -> CustomStuff4.proxy.registerItemModel(item, meta, model)));
    }

    protected abstract Optional<Item> createItem(boolean hasSubtypes);

    public static IProperty[] insertSubtype(IProperty... properties)
    {
        if (activeContent != null)
        {
            IProperty[] newPropertires = new IProperty[properties.length + 1];
            newPropertires[0] = BlockHelper.getSubtypeProperty(activeContent.subtypes);
            System.arraycopy(properties, 0, newPropertires, 1, properties.length);
            return newPropertires;
        } else
        {
            return properties;
        }
    }
}
