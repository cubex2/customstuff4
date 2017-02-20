package cubex2.cs4.util;

public class GuiHelper
{
    public static int calculateLeft(String alignment, int offset, int elemWidth, int guiWidth)
    {
        switch (alignment.toLowerCase())
        {
            case "left":
                return offset;
            case "right":
                return guiWidth - elemWidth + offset;
            case "center":
                return (guiWidth - elemWidth) / 2 + offset;
            default:
                return 0;
        }
    }

    public static int calculateTop(String alignment, int offset, int elemHeight, int guiHeight)
    {
        switch (alignment.toLowerCase())
        {
            case "top":
                return offset;
            case "bottom":
                return guiHeight - elemHeight + offset;
            case "center":
                return (guiHeight - elemHeight) / 2 + offset;
            default:
                return 0;
        }
    }
}
