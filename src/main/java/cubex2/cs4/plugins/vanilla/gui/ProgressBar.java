package cubex2.cs4.plugins.vanilla.gui;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonParseException;
import net.minecraftforge.fml.client.config.GuiUtils;

import java.util.Arrays;

public class ProgressBar
{
    public int x;
    public int y;
    public int texX;
    public int texY;
    public int width;
    public int height;
    public String source;
    public Direction direction = Direction.RIGHT;

    public void draw(ProgressBarSource progressSource)
    {
        float progress = progressSource.getProgress(this.source);

        int w = direction.getWidth(width, progress);
        int h = direction.getHeight(height, progress);

        int offsetX = direction.getOffsetX(width, w);
        int offsetY = direction.getOffsetY(height, h);

        GuiUtils.drawTexturedModalRect(x + offsetX, y + offsetY, texX + offsetX, texY + offsetY, w, h, 0f);
    }

    public enum Direction
    {
        UP("up"), DOWN("down"), LEFT("left"), RIGHT("right");

        public final String name;

        Direction(String name)
        {
            this.name = name;
        }

        int getWidth(int maxWidth, float progress)
        {
            if (this == UP || this == DOWN)
                return maxWidth;

            return Math.round(maxWidth * progress);
        }

        int getOffsetX(int maxWidth, int width)
        {
            if (this != LEFT)
                return 0;

            return maxWidth - width;
        }

        int getHeight(int maxHeight, float progress)
        {
            if (this == LEFT || this == RIGHT)
                return maxHeight;

            return Math.round(maxHeight * progress);
        }

        int getOffsetY(int maxHeight, int height)
        {
            if (this != UP)
                return 0;

            return maxHeight - height;
        }

        public static final JsonDeserializer<Direction> DESERIALIZER = (json, typeOfT, context) ->
                Arrays.stream(values())
                      .filter(d -> d.name.equals(json.getAsString()))
                      .findFirst()
                      .orElseThrow(() -> new JsonParseException("Invalid direction: " + json.toString()));
    }
}
