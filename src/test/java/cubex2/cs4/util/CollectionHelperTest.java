package cubex2.cs4.util;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Collection Helper")
public class CollectionHelperTest
{
    @Test
    @DisplayName("equals whitout order")
    public void equalsWithoutOrder()
    {
        assertFalse(CollectionHelper.equalsWithoutOrder(Lists.newArrayList("a"), Lists.newArrayList("a", "a"), Objects::equals));
        assertFalse(CollectionHelper.equalsWithoutOrder(Lists.newArrayList("a"), Lists.newArrayList("b"), Objects::equals));
        assertFalse(CollectionHelper.equalsWithoutOrder(Lists.newArrayList("a", "a", "b"), Lists.newArrayList("a", "b", "b"), Objects::equals));
        assertTrue(CollectionHelper.equalsWithoutOrder(Lists.newArrayList("a", "a", "b"), Lists.newArrayList("a", "a", "b"), Objects::equals));
        assertTrue(CollectionHelper.equalsWithoutOrder(Lists.newArrayList("a", "b"), Lists.newArrayList("b", "a"), Objects::equals));
    }
    @Nested
    @DisplayName("Transposing")
    class Transposing{
        @Test
        @DisplayName("empty list")
        public void transposeEmpty()
        {
            List<List<Integer>> input = Collections.emptyList();
            List<List<Integer>> output = CollectionHelper.transpose(input);

            assertSame(input, output);
        }

        @Test()
        @DisplayName("non rectangular structure throws IllegalArgumentException")
        public void transposeNonRectangular()
        {
            List<List<Integer>> input = Lists.newArrayList();
            input.add(Lists.newArrayList(1));
            input.add(Lists.newArrayList(1, 2));
            assertThrows(IllegalArgumentException.class,()-> CollectionHelper.transpose(input));
        }

        @Test
        @DisplayName("transpose rectangular")
        public void transposeRectangular()
        {
            List<List<Integer>> input = Lists.newArrayList();
            input.add(Lists.newArrayList(1, 2, 3));
            input.add(Lists.newArrayList(4, 5, 6));

            List<List<Integer>> output = CollectionHelper.transpose(input);

            List<List<Integer>> expected = Lists.newArrayList();
            expected.add(Lists.newArrayList(1, 4));
            expected.add(Lists.newArrayList(2, 5));
            expected.add(Lists.newArrayList(3, 6));

            assertEquals(expected, output);
        }

        @Test
        @DisplayName("transpose quadratic")
        public void transposeQuadratic()
        {
            List<List<Integer>> input = Lists.newArrayList();
            input.add(Lists.newArrayList(1, 2));
            input.add(Lists.newArrayList(3, 4));

            List<List<Integer>> output = CollectionHelper.transpose(input);

            List<List<Integer>> expected = Lists.newArrayList();
            expected.add(Lists.newArrayList(1, 3));
            expected.add(Lists.newArrayList(2, 4));

            assertEquals(expected, output);
        }

    }

    @Nested
    @DisplayName("Make same size")
    class MakeSameSize{
        @Test
        @DisplayName("empty list")
        public void makeSameSizeEmpty()
        {
            List<List<Integer>> input = Lists.newArrayList();
            List<List<Integer>> output = CollectionHelper.makeSameSize(input, 0);

            List<List<Integer>> expected = Lists.newArrayList();
            assertEquals(output, expected);
        }

        @Test
        @DisplayName("already the same size")
        public void makeSameSizeAlreadySameSize()
        {
            List<List<Integer>> input = Lists.newArrayList();
            input.add(Lists.newArrayList(1, 2));
            input.add(Lists.newArrayList(3, 4));
            List<List<Integer>> output = CollectionHelper.makeSameSize(input, 0);

            List<List<Integer>> expected = Lists.newArrayList();
            expected.add(Lists.newArrayList(1, 2));
            expected.add(Lists.newArrayList(3, 4));
            assertEquals(output, expected);
        }


        @Test
        @DisplayName("not the same size")
        public void makeSameSizeNotSameSize()
        {
            List<List<Integer>> input = Lists.newArrayList();
            input.add(Lists.newArrayList(1, 2));
            input.add(Lists.newArrayList(3));
            input.add(Lists.newArrayList());
            List<List<Integer>> output = CollectionHelper.makeSameSize(input, 0);

            List<List<Integer>> expected = Lists.newArrayList();
            expected.add(Lists.newArrayList(1, 2));
            expected.add(Lists.newArrayList(3, 0));
            expected.add(Lists.newArrayList(0, 0));
            assertEquals(output, expected);
        }

    }



}