package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.plugins.vanilla.block.BlockFactory;
import net.minecraft.block.Block;

public class ContentBlockOrientableVertical extends ContentBlockOrientable
{
    @Override
    protected Block createBlockWithSubtypes()
    {
        return BlockFactory.createVerticalSubtype(this);
    }

    @Override
    protected Block createBlockWithoutSubtypes()
    {
        return BlockFactory.createVertical(this);
    }
}
