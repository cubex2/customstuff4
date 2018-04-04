package cubex2.cs4.util;

import cubex2.cs4.plugins.vanilla.block.EnumSubtype;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.Assert.*;

@DisplayName("Block helpers")
public class BlockHelperTests {
    @Nested
    @DisplayName("Get subtype property")
    class Subtypes {

        @Test
        @DisplayName("order does not matter")
        public void test_getSubtypeProperty_subtypeOrderDoesNotMatter() {
            PropertyEnum<EnumSubtype> property1 = BlockHelper.getSubtypeProperty(new int[]{0, 5});
            PropertyEnum<EnumSubtype> property2 = BlockHelper.getSubtypeProperty(new int[]{5, 0});

            assertSame(property1, property2);
        }

        @Test
        @DisplayName("multiple Calls return the same property")
        public void test_getSubtypeProperty_multipleCallsReturnsSameProperty() {
            PropertyEnum<EnumSubtype> property1 = BlockHelper.getSubtypeProperty(new int[]{0, 5});
            PropertyEnum<EnumSubtype> property2 = BlockHelper.getSubtypeProperty(new int[]{0, 5});

            assertSame(property1, property2);
        }

        @Test
        @DisplayName("property")
        public void test_getSubtypeProperty() {
            PropertyEnum<EnumSubtype> property = BlockHelper.getSubtypeProperty(new int[]{0, 5});
            Collection<EnumSubtype> allowedValues = property.getAllowedValues();

            assertEquals(2, allowedValues.size());
            assertTrue(allowedValues.contains(EnumSubtype.SUBTYPE0));
            assertTrue(allowedValues.contains(EnumSubtype.SUBTYPE5));
        }

        @Nested
        @DisplayName("duplicates")
        class Duplicates {
            @Test
            @DisplayName("Ignored")
            public void test_getSubtypeProperty_ignoresDuplicates() {
                PropertyEnum<EnumSubtype> property = BlockHelper.getSubtypeProperty(new int[]{0, 0, 5, 5});
                Collection<EnumSubtype> allowedValues = property.getAllowedValues();

                assertEquals(2, allowedValues.size());
                assertTrue(allowedValues.contains(EnumSubtype.SUBTYPE0));
                assertTrue(allowedValues.contains(EnumSubtype.SUBTYPE5));
            }

            @Test
            @DisplayName("is the same as if the duplicates weren't there")
            public void test_getSubtypeProperty_ignoresDuplicates2() {
                PropertyEnum<EnumSubtype> property1 = BlockHelper.getSubtypeProperty(new int[]{0, 0, 5, 5});
                PropertyEnum<EnumSubtype> property2 = BlockHelper.getSubtypeProperty(new int[]{0, 5});

                assertSame(property1, property2);
            }
        }


    }

    @Nested
    @DisplayName("Get crop age property")
    class Crop {
        @Test
        @DisplayName("can get")
        public void test_getCropAgeProperty() {
            PropertyInteger property1 = BlockHelper.getCropAgeProperty(9);

            assertEquals(10, property1.getAllowedValues().size());
            assertTrue(property1.getAllowedValues().contains(0));
            assertTrue(property1.getAllowedValues().contains(9));
        }

        @Test
        @DisplayName("calling multiple times returns the same")
        public void test_getCropAgeProperty_multipleCallsReturnsSameProperty() {
            PropertyInteger property1 = BlockHelper.getCropAgeProperty(9);
            PropertyInteger property2 = BlockHelper.getCropAgeProperty(9);

            assertEquals(10, property1.getAllowedValues().size());
            assertSame(property1, property2);
        }
    }


}
