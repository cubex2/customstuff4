package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.plugins.vanilla.block.BlockOrientable;
import cubex2.cs4.plugins.vanilla.block.BlockOrientableHorizontal;
import cubex2.cs4.plugins.vanilla.block.BlockOrientableHorizontalWithSubtypes;

public class ContentBlockOrientableHorizontal extends ContentBlockOrientable
{
    @Override
    protected BlockOrientable createBlockWithSubtypes()
    {
        return new BlockOrientableHorizontalWithSubtypes(material, this);
    }

    @Override
    protected BlockOrientable createBlockWithoutSubtypes()
    {
        return new BlockOrientableHorizontal(material, this);
    }
}
