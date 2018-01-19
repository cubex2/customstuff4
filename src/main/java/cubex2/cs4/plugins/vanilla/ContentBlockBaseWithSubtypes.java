package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.CustomStuff4;
import cubex2.cs4.api.ContentHelper;
import cubex2.cs4.util.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import java.util.Collection;
import java.util.Optional;

public abstract class ContentBlockBaseWithSubtypes extends ContentBlockBase
{
    private static ContentBlockBaseWithSubtypes activeContent;

    public int[] subtypes = new int[0];
    private transient boolean hasSubtypes;

    @Override
    public final Block createBlock()
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
    protected void initItem(Item item, ContentHelper helper)
    {
        super.initItem(item, helper);

        item.setHasSubtypes(hasSubtypes);
    }

    @Override
    protected void registerModels()
    {
        if (item != null)
        {
            for (int meta : subtypes)
            {
                ResourceLocation model = itemModel.get(meta).orElse(item.getRegistryName());
                CustomStuff4.proxy.registerItemModel(item, meta, model);
            }
        }
    }

    protected abstract Optional<Item> createItem(boolean hasSubtypes);

    public static IProperty[] insertSubtype(Collection<IProperty<?>> properties)
    {
        if (activeContent != null)
        {
            IProperty[] newProperties = new IProperty[properties.size() + 1];
            newProperties[0] = BlockHelper.getSubtypeProperty(activeContent.subtypes);
            int i = 1;
            for (IProperty property : properties)
            {
                newProperties[i++] = property;
            }
            return newProperties;
        } else
        {
            return properties.toArray(new IProperty[0]);
        }
    }
}
