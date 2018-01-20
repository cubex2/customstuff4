package cubex2.cs4.util;

import com.google.common.collect.Maps;
import cubex2.cs4.plugins.vanilla.block.EnumSubtype;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;

public class BlockHelper
{
    private static final Map<IntArray, PropertyEnum<EnumSubtype>> subtypeProperties = Maps.newHashMap();
    private static final Map<Integer, PropertyInteger> cropAgeProperties = Maps.newHashMap();

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

    /**
     * Gets the property for the given max age of a crop. Returns the same instance of a property for the same max age.
     */
    @Nonnull
    public static PropertyInteger getCropAgeProperty(int maxAge)
    {
        checkArgument(maxAge >= 0 && maxAge <= 15, "Invalid maxAge : " + maxAge);

        return cropAgeProperties.computeIfAbsent(maxAge, BlockHelper::createCropAgeProperty);
    }

    private static PropertyInteger createCropAgeProperty(int maxAge)
    {
        return PropertyInteger.create("age", 0, maxAge);
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
