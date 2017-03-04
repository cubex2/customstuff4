package cubex2.cs4.plugins.vanilla.block;

import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EnumSubtypeTests
{
    @Test
    public void test_getValues()
    {
        Collection<EnumSubtype> values = EnumSubtype.getValues(new int[] {0, 5, 9});

        assertTrue(values.contains(EnumSubtype.SUBTYPE0));
        assertTrue(values.contains(EnumSubtype.SUBTYPE5));
        assertTrue(values.contains(EnumSubtype.SUBTYPE9));
    }

    @Test
    public void test_getValues_empty()
    {
        Collection<EnumSubtype> values = EnumSubtype.getValues(new int[0]);

        assertTrue(values.isEmpty());
    }

    @Test
    public void test_getValues_removesDuplicates()
    {
        Collection<EnumSubtype> values = EnumSubtype.getValues(new int[] {1, 1, 2, 2});

        assertEquals(2, values.size());
    }
}
