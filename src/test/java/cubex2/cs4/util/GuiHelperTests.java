package cubex2.cs4.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

@DisplayName("Gui helper calculations")
public class GuiHelperTests {
    @Nested
    @DisplayName("left-")
    class Left {
        @Test
        @DisplayName("left")
        public void testCalculateLeft_left() {
            int left = GuiHelper.calculateLeft("left", 5, 100, 1000);

            assertEquals(5, left);
        }

        @Test
        @DisplayName("right")
        public void testCalculateLeft_right() {
            int left = GuiHelper.calculateLeft("right", -5, 100, 1000);

            assertEquals(895, left);
        }

        @Test
        @DisplayName("center")
        public void testCalculateLeft_center() {
            int left = GuiHelper.calculateLeft("center", -5, 100, 1000);

            assertEquals(445, left);
        }

    }

    @Nested
    @DisplayName("top-")
    class Top {
        @Test
        @DisplayName("top")
        public void testCalculateTop_top() {
            int top = GuiHelper.calculateTop("top", 5, 100, 1000);

            assertEquals(5, top);
        }

        @Test
        @DisplayName("right")
        public void testCalculateTop_right() {
            int top = GuiHelper.calculateTop("bottom", -5, 100, 1000);

            assertEquals(895, top);
        }

        @Test
        @DisplayName("center")
        public void testCalculateTop_center() {
            int top = GuiHelper.calculateTop("center", -5, 100, 1000);

            assertEquals(445, top);
        }
    }


}
