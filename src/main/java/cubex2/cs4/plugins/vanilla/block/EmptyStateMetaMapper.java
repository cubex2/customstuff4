package cubex2.cs4.plugins.vanilla.block;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;

public class EmptyStateMetaMapper<B extends Block> implements StateMetaMapper<B>
{
    @Override
    public IBlockState getStateFromMeta(B block, int meta)
    {
        return block.getDefaultState();
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return 0;
    }
}
