package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.plugins.vanilla.block.BlockFactory;
import cubex2.cs4.plugins.vanilla.block.ItemBlock;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.util.Optional;

public class ContentBlockTorch extends ContentBlockBaseNoSubtypes
{
    public boolean spawnParticles = true;

    public ContentBlockTorch()
    {
        opacity = Attribute.constant(0);
        isOpaqueCube = Attribute.constant(false);
        isFullCube = Attribute.constant(false);
    }

    @Override
    protected Optional<Item> createItem()
    {
        return Optional.of(new ItemBlock(block, this));
    }

    @Override
    public Block createBlock()
    {
        return BlockFactory.createTorch(this);
    }
}
