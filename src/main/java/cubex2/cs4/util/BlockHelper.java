package cubex2.cs4.util;

import com.google.common.collect.Maps;
import cubex2.cs4.plugins.vanilla.block.EnumSubtype;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import java.util.Arrays;
import java.util.Map;

public class BlockHelper
{
    private static final Map<IntArray, PropertyEnum<EnumSubtype>> subtypeProperties = Maps.newHashMap();

    /**
     * Gets the property for the given subtypes. Returns the same instance of a property for the same array of subtypes.
     * Order of the subtypes does not matter.
     */
    public static PropertyEnum<EnumSubtype> getSubtypeProperty(int[] subtypes)
    {
        int[] sortedSubtypes = Arrays.stream(subtypes)
                                     .distinct()
                                     .sorted()
                                     .toArray();

        return subtypeProperties.computeIfAbsent(new IntArray(sortedSubtypes), BlockHelper::createSubtypeProperty);
    }

    private static PropertyEnum<EnumSubtype> createSubtypeProperty(IntArray subtypes)
    {
        return PropertyEnum.create("subtype", EnumSubtype.class, EnumSubtype.getValues(subtypes.array));
    }

    public static EnumFacing getVerticalFacingFromEntity(BlockPos pos, EntityLivingBase living)
    {
        double d = living.posY + living.getEyeHeight();

        if (d - pos.getY() > 2.0D)
            return EnumFacing.UP;
        else
            return EnumFacing.DOWN;
    }

    private static class IntArray
    {
        private final int[] array;

        private IntArray(int[] array) {this.array = array;}

        @Override
        public boolean equals(Object o)
        {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            IntArray intArray = (IntArray) o;

            return Arrays.equals(array, intArray.array);
        }

        @Override
        public int hashCode()
        {
            return Arrays.hashCode(array);
        }
    }
}
