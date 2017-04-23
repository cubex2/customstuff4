package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.plugins.vanilla.tileentity.TileEntitySimple;
import net.minecraft.tileentity.TileEntity;

public class ContentTileEntitySimple extends ContentTileEntityBase
{
    @Override
    protected Class<? extends TileEntity> getTemplateClass()
    {
        return TileEntitySimple.class;
    }
}
