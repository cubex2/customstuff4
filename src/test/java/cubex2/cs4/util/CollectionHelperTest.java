package cubex2.cs4.util;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CollectionHelperTest
{
    @Test
    public void equalsWithoutOrder()
    {
        assertFalse(CollectionHelper.equalsWithoutOrder(Lists.newArrayList("a"), Lists.newArrayList("a", "a"), Objects::equals));
        assertFalse(CollectionHelper.equalsWithoutOrder(Lists.newArrayList("a"), Lists.newArrayList("b"), Objects::equals));
        assertFalse(CollectionHelper.equalsWithoutOrder(Lists.newArrayList("a", "a", "b"), Lists.newArrayList("a", "b", "b"), Objects::equals));
        assertTrue(CollectionHelper.equalsWithoutOrder(Lists.newArrayList("a", "a", "b"), Lists.newArrayList("a", "a", "b"), Objects::equals));
        assertTrue(CollectionHelper.equalsWithoutOrder(Lists.newArrayList("a", "b"), Lists.newArrayList("b", "a"), Objects::equals));
    }

}