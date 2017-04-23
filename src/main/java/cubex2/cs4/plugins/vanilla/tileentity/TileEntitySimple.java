package cubex2.cs4.plugins.vanilla.tileentity;

import cubex2.cs4.plugins.vanilla.ContentTileEntitySimple;
import cubex2.cs4.plugins.vanilla.TileEntityRegistry;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public abstract class TileEntitySimple extends TileEntity implements CSTileEntity<ContentTileEntitySimple>
{
    private final ContentTileEntitySimple content;

    public TileEntitySimple()
    {
        content = (ContentTileEntitySimple) TileEntityRegistry.getContent(new ResourceLocation(getContentId()));
    }

    @Override
    public ContentTileEntitySimple getContent()
    {
        return content;
    }
}
