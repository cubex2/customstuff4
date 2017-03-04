package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.plugins.vanilla.block.BlockSimple;
import cubex2.cs4.plugins.vanilla.block.BlockSimpleWithSubtypes;
import cubex2.cs4.plugins.vanilla.block.ItemBlock;
import net.minecraft.item.Item;

import java.util.Optional;

public class ContentBlockSimple extends ContentBlockBaseWithSubtypes<BlockSimple>
{
    @Override
    protected BlockSimple createBlockWithSubtypes()
    {
        return new BlockSimpleWithSubtypes(material, this);
    }

    @Override
    protected BlockSimple createBlockWithoutSubtypes()
    {
        return new BlockSimple(material, this);
    }

    @Override
    protected Optional<Item> createItem(boolean hasSubtypes)
    {
        return Optional.of(new ItemBlock(block, this));
    }
}
