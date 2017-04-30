package cubex2.cs4.api;

public interface Length
{
    /**
     * Returns the length value.
     *
     * @param relValue The reference for relative values. For example if this is a relative length of 50%, it'll return
     *                 relValue * 0.5. This is unused for absolute values.
     */
    int getLength(int relValue);

    Length ZERO = relValue -> 0;
}
