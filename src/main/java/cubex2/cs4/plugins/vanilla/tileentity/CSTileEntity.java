package cubex2.cs4.plugins.vanilla.tileentity;

import cubex2.cs4.plugins.vanilla.ContentTileEntityBase;

public interface CSTileEntity<T extends ContentTileEntityBase>
{
    T getContent();
}
