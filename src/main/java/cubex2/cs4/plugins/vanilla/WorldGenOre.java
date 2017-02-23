package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.api.ContentHelper;
import cubex2.cs4.api.InitPhase;
import cubex2.cs4.data.SimpleContent;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nullable;
import java.util.Random;

public class WorldGenOre extends SimpleContent implements IWorldGenerator
{
    ResourceLocation block;
    int meta;
    int count = 1;
    int size = 10;
    int weight = 1;
    int minHeight = 0;
    int maxHeight = 64;
    ResourceLocation target;
    int targetMeta = -1;
    int dimension = 0;

    private transient WorldGenMinable gen;
    private transient Block targetBlock;

    @Override
    protected void doInit(InitPhase phase, ContentHelper helper)
    {
        if (target != null)
            targetBlock = Block.REGISTRY.getObject(target);

        createGenerator();

        GameRegistry.registerWorldGenerator(this, weight);
    }

    private void createGenerator()
    {
        Block block = Block.REGISTRY.getObject(this.block);
        IBlockState state = block.getStateFromMeta(meta);

        if (target == null)
        {
            gen = new WorldGenMinable(state, size);
        } else
        {
            gen = new WorldGenMinable(state, size, this::canGenerate);
        }
    }

    private boolean canGenerate(@Nullable IBlockState state)
    {
        return state != null && isCorrectState(state);
    }

    private boolean isCorrectState(IBlockState state)
    {
        return state.getBlock() == targetBlock && (targetMeta == -1 || targetMeta == targetBlock.getMetaFromState(state));
    }

    @Override
    protected boolean isReady()
    {
        boolean targetReady = target == null || target.equals(Blocks.AIR.getRegistryName()) || Block.REGISTRY.getObject(target) != Blocks.AIR;
        boolean blockReady = block == null || Block.REGISTRY.getObject(block) != Blocks.AIR;

        return targetReady && blockReady;
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
        if (world.provider.getDimension() != dimension)
        {
            return;
        }

        BlockPos chunkPos = new BlockPos(chunkX * 16, 0, chunkZ * 16);

        if (maxHeight < minHeight)
        {
            int i = minHeight;
            minHeight = maxHeight;
            maxHeight = i;
        } else if (maxHeight == minHeight)
        {
            if (minHeight < 255)
            {
                ++maxHeight;
            } else
            {
                --minHeight;
            }
        }

        for (int j = 0; j < count; ++j)
        {
            BlockPos blockpos = chunkPos.add(random.nextInt(16), random.nextInt(maxHeight - minHeight) + minHeight, random.nextInt(16));
            gen.generate(world, random, blockpos);
        }
    }
}
