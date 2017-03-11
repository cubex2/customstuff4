package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.CustomStuff4;
import cubex2.cs4.util.IntRange;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import java.util.Arrays;
import java.util.Optional;

public abstract class ContentBlockBaseWithSubtypes<T extends Block> extends ContentBlockBase<T>
{
    private static ContentBlockBaseWithSubtypes<?> activeContent;

    public int[] subtypes = new int[0];
    private transient boolean hasSubtypes;

    @Override
    protected final T createBlock()
    {
        hasSubtypes = subtypes.length > 0;

        if (subtypes.length == 0)
            subtypes = new int[] {0};

        activeContent = this;

        T block;
        if (hasSubtypes)
            block = createBlockWithSubtypes();
        else
            block = createBlockWithoutSubtypes();

        activeContent = null;

        return block;
    }

    protected abstract T createBlockWithSubtypes();

    protected abstract T createBlockWithoutSubtypes();

    @Override
    protected final Optional<Item> createItem()
    {
        Optional<Item> item = createItem(hasSubtypes);

        item.ifPresent(this::initItem);

        return item;
    }

    private void initItem(Item item)
    {
        item.setHasSubtypes(hasSubtypes);
        Arrays.stream(subtypes).forEach(meta -> itemModel.get(meta).ifPresent(model -> CustomStuff4.proxy.registerItemModel(item, meta, model)));
    }

    protected abstract Optional<Item> createItem(boolean hasSubtypes);

    /**
     * Gets the current active content. This is only present in the constructor of the block.
     */
    public static Optional<ContentBlockBaseWithSubtypes<?>> getActiveContent()
    {
        return Optional.ofNullable(activeContent);
    }
}
