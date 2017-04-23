package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.plugins.vanilla.block.BlockFactory;
import cubex2.cs4.plugins.vanilla.block.ItemBlockWithSubtypes;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.util.Optional;

public class ContentBlockFence extends ContentBlockBaseWithSubtypes
{
    public ContentBlockFence()
    {
        opacity = Attribute.constant(0);
    }

    @Override
    protected Block createBlockWithSubtypes()
    {
        return BlockFactory.createFenceSubtype(this);
    }

    @Override
    protected Block createBlockWithoutSubtypes()
    {
        return BlockFactory.createFence(this);
    }

    @Override
    protected Optional<Item> createItem(boolean hasSubtypes)
    {
        return Optional.of(new ItemBlockWithSubtypes(block, this));
    }
}
