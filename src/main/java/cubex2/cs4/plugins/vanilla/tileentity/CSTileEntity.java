package cubex2.cs4.plugins.vanilla.tileentity;

import cubex2.cs4.plugins.vanilla.ContentTileEntityBase;

public interface CSTileEntity<T extends ContentTileEntityBase>
{
    T getContent();

    /**
     * This method is created by asm and should not be implemented manually
     */
    String getContentId();
}
