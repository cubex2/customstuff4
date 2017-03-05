package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.plugins.vanilla.block.BlockOrientable;
import cubex2.cs4.plugins.vanilla.block.BlockOrientableVertical;
import cubex2.cs4.plugins.vanilla.block.BlockOrientableVerticalWithSubtypes;

public class ContentBlockOrientableVertical extends ContentBlockOrientable
{
    @Override
    protected BlockOrientable createBlockWithSubtypes()
    {
        return new BlockOrientableVerticalWithSubtypes(material, this);
    }

    @Override
    protected BlockOrientable createBlockWithoutSubtypes()
    {
        return new BlockOrientableVertical(material, this);
    }
}
