package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.plugins.vanilla.block.BlockFactory;
import net.minecraft.block.Block;

public class ContentBlockOrientableDirectional extends ContentBlockOrientable
{
    @Override
    protected Block createBlockWithSubtypes()
    {
        return BlockFactory.createDirectionalSubtype(this);
    }

    @Override
    protected Block createBlockWithoutSubtypes()
    {
        return BlockFactory.createDirectional(this);
    }
}
