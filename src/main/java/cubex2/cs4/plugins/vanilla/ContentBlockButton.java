package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.plugins.vanilla.block.BlockFactory;
import cubex2.cs4.plugins.vanilla.block.ItemBlock;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.util.Optional;

public class ContentBlockButton extends ContentBlockBaseNoSubtypes
{
    public int pressedTicks = 20;
    public boolean triggeredByArrows = false;

    @Override
    protected Optional<Item> createItem()
    {
        return Optional.of(new ItemBlock(block, this));
    }

    @Override
    public Block createBlock()
    {
        return BlockFactory.createButton(this);
    }
}
