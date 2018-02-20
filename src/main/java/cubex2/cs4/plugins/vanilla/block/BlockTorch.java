package cubex2.cs4.plugins.vanilla.block;

import cubex2.cs4.plugins.vanilla.ContentBlockTorch;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockTorch extends net.minecraft.block.BlockTorch implements CSBlock<ContentBlockTorch>
{
    private final ContentBlockTorch content;

    public BlockTorch(Material material, ContentBlockTorch content)
    {
        this.content = content;
    }

    @Override
    public int getSubtype(IBlockState state)
    {
        return 0;
    }

    @Override
    public ContentBlockTorch getContent()
    {
        return content;
    }

    @Override
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
        if (content.spawnParticles)
        {
            super.randomDisplayTick(stateIn, worldIn, pos, rand);
        }
    }
}
