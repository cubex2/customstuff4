package cubex2.cs4.plugins.vanilla.block;

import cubex2.cs4.plugins.vanilla.ContentBlockStairs;
import cubex2.cs4.plugins.vanilla.WrappedBlockStateImpl;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Bootstrap;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Tuple;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Collection;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class BlockStairsTest
{
    @BeforeClass
    public static void setUp()
    {
        Bootstrap.register();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testProperties()
    {
        ContentBlockStairs content = new ContentBlockStairs();
        content.id = "test_getSubtype";
        content.modelState = new WrappedBlockStateImpl(new ResourceLocation("minecraft:log"),
                                                       Collections.singletonList(new Tuple<>("variant", "spruce")));

        Block block = content.createBlock();
        Collection<IProperty<?>> properties = block.getBlockState().getProperties();
        assertEquals(3, properties.size());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void test_getSubtype()
    {
        ContentBlockStairs content = new ContentBlockStairs();
        content.id = "test_getSubtype";
        content.modelState = new WrappedBlockStateImpl(new ResourceLocation("minecraft:log"),
                                                       Collections.singletonList(new Tuple<>("variant", "spruce")));

        Block block = content.createBlock();
        CSBlock<ContentBlockStairs> csblock = (CSBlock<ContentBlockStairs>) block;
        for (EnumFacing facing : BlockStairs.FACING.getAllowedValues())
        {
            for (net.minecraft.block.BlockStairs.EnumHalf half : BlockStairs.HALF.getAllowedValues())
            {
                for (net.minecraft.block.BlockStairs.EnumShape shape : BlockStairs.SHAPE.getAllowedValues())
                {
                    IBlockState state = block.getDefaultState()
                                             .withProperty(BlockStairs.FACING, facing)
                                             .withProperty(BlockStairs.HALF, half)
                                             .withProperty(BlockStairs.SHAPE, shape);

                    assertEquals(0, csblock.getSubtype(state));
                }
            }
        }
    }
}