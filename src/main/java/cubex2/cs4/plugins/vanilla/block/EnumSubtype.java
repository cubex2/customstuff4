package cubex2.cs4.plugins.vanilla.block;

import net.minecraft.util.IStringSerializable;

public enum EnumSubtype implements IStringSerializable
{
    SUBTYPE0,
    SUBTYPE1,
    SUBTYPE2,
    SUBTYPE3,
    SUBTYPE4,
    SUBTYPE5,
    SUBTYPE6,
    SUBTYPE7,
    SUBTYPE8,
    SUBTYPE9,
    SUBTYPE10,
    SUBTYPE11,
    SUBTYPE12,
    SUBTYPE13,
    SUBTYPE14,
    SUBTYPE15;

    @Override
    public String getName()
    {
        return name().toLowerCase();
    }
}
