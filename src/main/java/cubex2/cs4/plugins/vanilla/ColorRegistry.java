package cubex2.cs4.plugins.vanilla;

public interface ColorRegistry
{
    /**
     * Get the color for the given name. The case of the given name does change the result.
     *
     * @return The associated color or -1 (white) if no color is associated with that name.
     */
    int getColor(String name);
}
