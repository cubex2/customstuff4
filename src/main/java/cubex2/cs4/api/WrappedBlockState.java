package cubex2.cs4.api;

import net.minecraft.block.state.IBlockState;

import javax.annotation.Nullable;

public interface WrappedBlockState
{
    @Nullable
    IBlockState createState();
}
