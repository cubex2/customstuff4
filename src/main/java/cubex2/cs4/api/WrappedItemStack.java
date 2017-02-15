package cubex2.cs4.api;

import net.minecraft.item.ItemStack;

/**
 * Contains the data to construct an ItemStack. Necessary as deserialization happens during preInit while items can be
 * registered later.
 */
public interface WrappedItemStack
{
    /**
     * Tries to create the ItemStack. This returns ItemStack.EMPTY if the item for the stack does not exist or hasn't been loaded
     * yet.
     */
    ItemStack createItemStack();

    /**
     * Returns true if the item for stack has been loaded. Allows to distinguish between empty stacks due to missing items
     * or due to an explicit empty stack defined in json.
     */
    boolean isItemLoaded();
}
