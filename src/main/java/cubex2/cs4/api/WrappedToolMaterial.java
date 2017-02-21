package cubex2.cs4.api;

import net.minecraft.item.Item;

/**
 * Contains the data to get a ToolMaterial. Necessary as deserialization happens during preInit while materials can be
 * registered later.
 */
public interface WrappedToolMaterial
{
    /**
     * Gets the material. Returns null if the material does not exist.
     */
    Item.ToolMaterial getToolMaterial();

    static WrappedToolMaterial of(Item.ToolMaterial material)
    {
        return () -> material;
    }
}
