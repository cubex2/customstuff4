package cubex2.cs4.plugins.vanilla.gui;

import net.minecraftforge.items.IItemHandler;

import java.util.Optional;

public interface ItemHandlerSupplier
{
    /**
     * Get an IItemHandler for the given name. This is used in containers to get the actual IItemHandler from the name
     * specified in the json file.
     */
    Optional<IItemHandler> getItemHandler(String name);
}
