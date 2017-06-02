package cubex2.cs4.plugins.vanilla.gui;

public class SlotData
{
    public String name;
    int firstSlot = 0;
    int rows = 1;
    int columns = 1;
    int x = 0;
    int y = 0;
    boolean dropOnClose = false;
    int spacingX = 18;
    int spacingY = 18;

    int getSlotIndex(int row, int col)
    {
        return firstSlot + row * columns + col;
    }

    public int getX(int col)
    {
        return x + col * spacingX;
    }

    public int getY(int row)
    {
        return y + row * spacingY;
    }

    public int getRow(int index)
    {
        return index / columns;
    }

    public int getColumn(int index)
    {
        return index % columns;
    }

    public boolean containsIndex(int index)
    {
        return index >= firstSlot && index <= firstSlot + rows * columns - 1;
    }
}
