package cubex2.cs4.util;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.RandomUtils;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.ToIntFunction;

import static com.google.common.base.Preconditions.checkArgument;

public class CollectionHelper
{
    public static <T, U> boolean equalsWithoutOrder(List<T> fst, List<U> snd, BiPredicate<T, U> equality)
    {
        if (fst != null && snd != null)
        {
            if (fst.size() == snd.size())
            {
                // create copied lists so the original list is not modified
                List<T> cfst = new ArrayList<>(fst);
                List<U> csnd = new ArrayList<>(snd);

                Iterator<T> ifst = cfst.iterator();
                boolean foundEqualObject;
                while (ifst.hasNext())
                {
                    T a = ifst.next();
                    Iterator<U> isnd = csnd.iterator();
                    foundEqualObject = false;
                    while (isnd.hasNext())
                    {
                        U b = isnd.next();
                        if (equality.test(a, b))
                        {
                            ifst.remove();
                            isnd.remove();
                            foundEqualObject = true;
                            break;
                        }
                    }

                    if (!foundEqualObject)
                    {
                        // fail early
                        break;
                    }
                }
                if (cfst.isEmpty())
                { //both temporary lists have the same size
                    return true;
                }
            }
        } else if (fst == null && snd == null)
        {
            return true;
        }
        return false;
    }

    /**
     * Gets a random element from the given collection where each element has a weight provided by weightFunction.
     * A higher weight makes an element more likely to be selected.
     *
     * @return A random element or null if the collection was empty.
     */
    @Nonnull
    public static <T> Optional<T> randomElement(Collection<T> collection, ToIntFunction<T> weightFunction)
    {
        int totalWeight = collection.stream().mapToInt(weightFunction).sum();
        int randomWeight = RandomUtils.nextInt(1, totalWeight + 1);

        for (T t : collection)
        {
            randomWeight -= weightFunction.applyAsInt(t);
            if (randomWeight <= 0)
                return Optional.of(t);
        }

        return Optional.empty();
    }

    /**
     * Transpose the matrix represented by the list of lists. All lists must be of the same size. If the list is empty,
     * this will just return the list.
     */
    public static <T> List<List<T>> transpose(List<List<T>> list)
    {
        if (list.isEmpty())
            return list;

        int width = list.get(0).size();
        int height = list.size();
        checkArgument(list.stream().allMatch(l -> l.size() == width), "Not all lists have the same size");

        List<List<T>> result = Lists.newArrayListWithExpectedSize(width);
        for (int i = 0; i < width; i++)
        {
            result.add(Lists.newArrayListWithExpectedSize(height));
        }

        for (int x = 0; x < width; x++)
        {
            for (int y = 0; y < height; y++)
            {
                result.get(x).add(list.get(y).get(x));
            }
        }

        return result;
    }

    /**
     * Makes every list in the list the same size by inserting filler into the lists. Returns the list.
     */
    public static <T> List<List<T>> makeSameSize(List<List<T>> list, T filler)
    {
        int maxSize = list.stream().mapToInt(Collection::size).max().orElse(0);

        for (List<T> ts : list)
        {
            while (ts.size() < maxSize)
                ts.add(filler);
        }

        return list;
    }
}
