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

    int getX(int col)
    {
        return x + col * spacingX;
    }

    int getY(int row)
    {
        return y + row * spacingY;
    }
}
