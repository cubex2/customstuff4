package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.api.WrappedBlockState;
import cubex2.cs4.plugins.vanilla.block.BlockFactory;
import cubex2.cs4.plugins.vanilla.block.ItemBlockWithSubtypes;
import cubex2.cs4.plugins.vanilla.block.ItemSlab;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.util.Optional;

public class ContentBlockSlab extends ContentBlockBaseWithSubtypes
{
    public Attribute<WrappedBlockState> doubleSlab = Attribute.constant(null);

    public ContentBlockSlab()
    {
        opacity = Attribute.constant(0);
    }

    @Override
    protected Block createBlockWithSubtypes()
    {
        return BlockFactory.createSlabSubtype(this);
    }

    @Override
    protected Block createBlockWithoutSubtypes()
    {
        return BlockFactory.createSlab(this);
    }

    @Override
    protected Optional<Item> createItem(boolean hasSubtypes)
    {
        return Optional.of(new ItemSlab(block, this));
    }
}
