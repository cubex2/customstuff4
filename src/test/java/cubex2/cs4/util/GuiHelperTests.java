package cubex2.cs4.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GuiHelperTests
{
    @Test
    public void testCalculateLeft_left()
    {
        int left = GuiHelper.calculateLeft("left", 5, 100, 1000);

        assertEquals(5, left);
    }

    @Test
    public void testCalculateLeft_right()
    {
        int left = GuiHelper.calculateLeft("right", -5, 100, 1000);

        assertEquals(895, left);
    }

    @Test
    public void testCalculateLeft_center()
    {
        int left = GuiHelper.calculateLeft("center", -5, 100, 1000);

        assertEquals(445, left);
    }

    @Test
    public void testCalculateTop_top()
    {
        int top = GuiHelper.calculateTop("top", 5, 100, 1000);

        assertEquals(5, top);
    }

    @Test
    public void testCalculateTop_right()
    {
        int top = GuiHelper.calculateTop("bottom", -5, 100, 1000);

        assertEquals(895, top);
    }

    @Test
    public void testCalculateTop_center()
    {
        int top = GuiHelper.calculateTop("center", -5, 100, 1000);

        assertEquals(445, top);
    }
}
