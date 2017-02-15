package cubex2.cs4.api;

public interface RecipeInput
{
    boolean isOreClass();

    boolean isItemStack();

    /**
     * Gets the ore class. Throws exception if isOreClass() returns false.
     */
    String getOreClass() throws IllegalStateException;

    /**
     * Gets the stack. Throws exception if isItemStack() returns false.
     */
    WrappedItemStack getStack() throws IllegalStateException;
}
