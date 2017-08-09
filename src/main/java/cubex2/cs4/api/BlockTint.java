package cubex2.cs4.api;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public interface BlockTint
{
    int getMultiplier(IBlockAccess world, BlockPos pos);

    BlockTint WHITE = (world, pos) -> -1;
}
