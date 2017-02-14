package cubex2.cs4.api;

import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

/**
 * Contains the data to construct an ItemStack. Necessary as deserialization happens during preInit while items can be
 * registered later.
 */
public interface WrappedItemStack
{
    /**
     * Tries to create the ItemStack. This returns null if the item for the stack does not exist or hasn't been loaded
     * yet.
     */
    @Nullable
    ItemStack createItemStack();
}
