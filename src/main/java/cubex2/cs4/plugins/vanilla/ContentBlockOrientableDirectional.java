package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.plugins.vanilla.block.BlockOrientable;
import cubex2.cs4.plugins.vanilla.block.BlockOrientableDirectional;
import cubex2.cs4.plugins.vanilla.block.BlockOrientableDirectionalWithSubtypes;

public class ContentBlockOrientableDirectional extends ContentBlockOrientable
{
    @Override
    protected BlockOrientable createBlockWithSubtypes()
    {
        return new BlockOrientableDirectionalWithSubtypes(material, this);
    }

    @Override
    protected BlockOrientable createBlockWithoutSubtypes()
    {
        return new BlockOrientableDirectional(material, this);
    }
}
