package cubex2.cs4.plugins.vanilla.block;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;

import java.util.Collection;

public interface StateMetaMapper<T extends Block>
{
    IBlockState getStateFromMeta(T block, int meta);

    int getMetaFromState(IBlockState state);

    @SuppressWarnings("unchecked")
    static <T extends Block> StateMetaMapper<T> create(Collection<IProperty<?>> properties)
    {
        if (properties.size() == 0)
            return new EmptyStateMetaMapper<>();
        else if (properties.size() == 1)
            return new SimpleStateMetaMapper(properties.iterator().next());
        else
            return new BitStateMetaMapper<>(properties);
    }
}
