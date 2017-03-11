package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.plugins.vanilla.block.BlockFactory;
import cubex2.cs4.plugins.vanilla.block.BlockOrientable;
import cubex2.cs4.plugins.vanilla.block.BlockOrientableHorizontal;
import cubex2.cs4.plugins.vanilla.block.BlockOrientableHorizontalWithSubtypes;
import net.minecraft.block.Block;

public class ContentBlockOrientableHorizontal extends ContentBlockOrientable
{
    @Override
    protected Block createBlockWithSubtypes()
    {
        return BlockFactory.createHorizontalSubtype(this);
    }

    @Override
    protected Block createBlockWithoutSubtypes()
    {
        return BlockFactory.createHorizontal(this);
    }
}
