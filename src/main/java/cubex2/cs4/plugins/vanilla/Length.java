package cubex2.cs4.plugins.vanilla;

public interface Length
{
    /**
     * Returns the length value.
     *
     * @param maxValue The maximum value that this should return.
     */
    int getLength(int maxValue);

    Length ZERO = parent -> 0;
}
