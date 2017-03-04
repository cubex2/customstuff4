package cubex2.cs4.plugins.vanilla.block;

import net.minecraft.util.IStringSerializable;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

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

    public static Collection<EnumSubtype> getValues(int[] ordinals)
    {
        return Arrays.stream(ordinals)
                     .mapToObj(meta -> EnumSubtype.values()[meta])
                     .distinct()
                     .collect(Collectors.toList());
    }
}
