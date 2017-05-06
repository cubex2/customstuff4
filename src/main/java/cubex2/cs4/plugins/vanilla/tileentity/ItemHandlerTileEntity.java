package cubex2.cs4.plugins.vanilla.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.ItemStackHandler;

public class ItemHandlerTileEntity extends ItemStackHandler
{
    protected final TileEntity tile;

    public ItemHandlerTileEntity(TileEntity tile)
    {
        this.tile = tile;
    }

    public ItemHandlerTileEntity(int size, TileEntity tile)
    {
        super(size);
        this.tile = tile;
    }

    @Override
    protected void onContentsChanged(int slot)
    {
        tile.markDirty();
    }
}
