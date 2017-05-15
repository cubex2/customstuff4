package cubex2.cs4.plugins.vanilla.gui;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SlotDataTest
{
    @Test
    public void test_getX()
    {
        SlotData data = new SlotData();
        data.spacingX = 20;
        data.x = 15;

        assertEquals(15, data.getX(0));
        assertEquals(55, data.getX(2));
    }

    @Test
    public void test_getY()
    {
        SlotData data = new SlotData();
        data.spacingY = 20;
        data.y = 15;

        assertEquals(15, data.getY(0));
        assertEquals(55, data.getY(2));
    }

    @Test
    public void test_getIndex()
    {
        SlotData data = new SlotData();
        data.rows = 2;
        data.columns = 3;

        assertEquals(0, data.getSlotIndex(0, 0));
        assertEquals(2, data.getSlotIndex(0, 2));
        assertEquals(3, data.getSlotIndex(1, 0));
        assertEquals(5, data.getSlotIndex(1, 2));
    }
}