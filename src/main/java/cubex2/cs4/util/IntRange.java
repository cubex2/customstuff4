package cubex2.cs4.util;

import java.util.Random;

import static com.google.common.base.Preconditions.checkArgument;

public abstract class IntRange
{
    public static final IntRange ZERO = create(0, 0);

    public abstract int getMin();

    public abstract int getMax();

    public abstract int getRandomValue();

    public static IntRange create(int min, int max)
    {
        if (min != max)
            return new RealRange(min, max);
        else
            return new SingleElementRange(min);
    }

    private static class SingleElementRange extends IntRange
    {
        private final int value;

        private SingleElementRange(int value) {this.value = value;}

        @Override
        public int getMin()
        {
            return value;
        }

        @Override
        public int getMax()
        {
            return value;
        }

        @Override
        public int getRandomValue()
        {
            return value;
        }
    }

    private static class RealRange extends IntRange
    {
        private static final Random RANDOM = new Random();

        private final int min;
        private final int max;

        public RealRange(int min, int max)
        {
            checkArgument(min <= max, "min > max!");

            this.min = min;
            this.max = max;
        }

        @Override
        public int getMin()
        {
            return min;
        }

        @Override
        public int getMax()
        {
            return max;
        }

        @Override
        public int getRandomValue()
        {
            return RANDOM.nextInt(max - min + 1) + min;
        }
    }
}
