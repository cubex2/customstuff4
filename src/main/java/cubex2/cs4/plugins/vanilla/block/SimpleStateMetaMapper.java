package cubex2.cs4.plugins.vanilla.block;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;

public class SimpleStateMetaMapper<T extends Comparable<T>, B extends Block> implements StateMetaMapper<B>
{
    final IProperty<T> property;
    private final ImmutableList<T> values;

    public SimpleStateMetaMapper(IProperty<T> property)
    {
        this.property = property;
        values = ImmutableList.copyOf(property.getAllowedValues());
    }

    @Override
    public IBlockState getStateFromMeta(B block, int meta)
    {
        return block.getDefaultState().withProperty(property, values.get(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return Math.max(values.indexOf(state.getValue(property)), 0);
    }
}
