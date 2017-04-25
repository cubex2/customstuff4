package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.api.TileEntityModuleSupplier;

public interface TileEntityModuleRegistry
{
    Class<? extends TileEntityModuleSupplier> getTileEntityModuleClass(String typeName);
}
