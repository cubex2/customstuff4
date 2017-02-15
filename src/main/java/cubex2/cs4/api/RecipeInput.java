package cubex2.cs4.api;

public interface RecipeInput
{
    boolean isOreClass();

    boolean isItemStack();

    /**
     * If isOreClass returns true, this will return a String representing the ore class. If isItemStack return true,
     * this will return either null or the ItemStack. Null is returned if the item doesn't exist or hasn't been loaded
     * yet.
     */
    Object getInput();
}
