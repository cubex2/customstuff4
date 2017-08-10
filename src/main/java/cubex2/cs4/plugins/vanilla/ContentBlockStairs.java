package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.api.WrappedBlockState;
import cubex2.cs4.plugins.vanilla.block.BlockFactory;
import cubex2.cs4.plugins.vanilla.block.ItemBlock;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.util.Optional;

public class ContentBlockStairs extends ContentBlockBaseNoSubtypes
{
    public WrappedBlockState modelState = null;

    public ContentBlockStairs()
    {
        opacity = Attribute.constant(0);
        isFullCube = Attribute.constant(false);
        isOpaqueCube = Attribute.constant(false);
    }

    @Override
    protected Optional<Item> createItem()
    {
        return Optional.of(new ItemBlock(block, this));
    }

    @Override
    protected Block createBlock()
    {
        return BlockFactory.createStairs(this);
    }

    @Override
    protected boolean isReady()
    {
        return modelState.createState() != null;
    }
}
