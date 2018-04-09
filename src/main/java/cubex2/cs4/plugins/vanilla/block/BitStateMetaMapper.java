package cubex2.cs4.plugins.vanilla.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;

import java.util.Collection;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

public class BitStateMetaMapper<B extends Block> implements StateMetaMapper<B>
{
    private static final int MAX_BITS = 4;

    private final List<Entry<?>> entries = Lists.newArrayList();
    private int totalBits = 0;

    public BitStateMetaMapper(Collection<IProperty<?>> properties)
    {
        properties.forEach(this::addProperty);
    }

    public BitStateMetaMapper(IProperty<?>... properties)
    {
        for (IProperty<?> property : properties)
        {
            addProperty(property);
        }
    }

    private <T extends Comparable<T>> void addProperty(IProperty<T> property)
    {
        int numBits = getBitCount(property.getAllowedValues().size());

        checkArgument(totalBits + numBits <= MAX_BITS, "Not enough bits left for " + property.getAllowedValues().size() + " values from property " + property.getName());

        entries.add(new Entry<>(property, numBits, totalBits));
        totalBits += numBits;
    }

    static int getBitCount(int n)
    {
        if (n == 1) return 1;

        int bits = 0;

        while (n > (1 << bits))
        {
            bits++;
        }

        return bits;
    }

    @Override
    public IBlockState getStateFromMeta(B block, int meta)
    {
        IBlockState state = block.getDefaultState();

        for (Entry<?> entry : entries)
        {
            state = entry.addToState(state, meta);
        }

        return state;
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        int meta = 0;

        for (Entry<?> entry : entries)
        {
            meta = meta | entry.getMetaBits(state);
        }

        return meta;
    }

    private static class Entry<T extends Comparable<T>>
    {
        final IProperty<T> property;
        final int bitOffset;
        final int numBits;
        final int bitMask;
        final List<T> values;

        Entry(IProperty<T> property, int numBits, int bitOffset)
        {
            checkArgument(numBits >= getBitCount(property.getAllowedValues().size()), "Too many values for " + numBits + " bits");

            this.property = property;
            this.bitOffset = bitOffset;
            this.numBits = numBits;
            this.bitMask = ((1 << numBits) - 1) << bitOffset;
            this.values = ImmutableList.copyOf(property.getAllowedValues());
        }

        IBlockState addToState(IBlockState state, int meta)
        {
            int index = (meta & bitMask) >> bitOffset;

            return state.withProperty(property, values.get(index));
        }

        int getMetaBits(IBlockState state)
        {
            T value = state.getValue(property);
            int index = values.indexOf(value);

            if (index >= 0)
            {
                return index << bitOffset;
            } else
            {
                return 0;
            }
        }
    }
}
