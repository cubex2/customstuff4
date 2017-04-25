package cubex2.cs4.api;

import net.minecraft.tileentity.TileEntity;

public interface TileEntityModuleSupplier
{
    TileEntityModule createModule(TileEntity tileEntity);
}
