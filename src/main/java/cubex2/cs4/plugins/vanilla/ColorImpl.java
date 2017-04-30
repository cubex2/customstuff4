package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.api.Color;

public class ColorImpl implements Color
{
    private final int rgb;

    public ColorImpl(int rgb) {this.rgb = rgb;}

    @Override
    public int getRGB()
    {
        return rgb;
    }
}
