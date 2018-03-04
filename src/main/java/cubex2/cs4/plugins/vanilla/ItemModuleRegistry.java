package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.api.ItemModuleSupplier;

public interface ItemModuleRegistry
{
    Class<? extends ItemModuleSupplier> getItemModuleClass(String typeName);
}
