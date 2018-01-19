package cubex2.cs4.plugins.vanilla.block;

import cubex2.cs4.plugins.vanilla.ContentBlockFence;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Bootstrap;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.assertEquals;

public class BlockFenceTest
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
        ContentBlockFence content = new ContentBlockFence();
        content.id = "test_getSubtype";

        Block block = content.createBlock();
        Collection<IProperty<?>> properties = block.getBlockState().getProperties();
        assertEquals(4, properties.size());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void test_getSubtype()
    {
        ContentBlockFence content = new ContentBlockFence();
        content.id = "test_getSubtype";

        Block block = content.createBlock();
        CSBlock<ContentBlockFence> csblock = (CSBlock<ContentBlockFence>) block;
        for (Boolean north : BlockFence.NORTH.getAllowedValues())
        {
            for (Boolean south : BlockFence.SOUTH.getAllowedValues())
            {
                for (Boolean east : BlockFence.EAST.getAllowedValues())
                {
                    for (Boolean west : BlockFence.WEST.getAllowedValues())
                    {
                        IBlockState state = block.getDefaultState()
                                                 .withProperty(BlockFence.NORTH, north)
                                                 .withProperty(BlockFence.SOUTH, north)
                                                 .withProperty(BlockFence.EAST, north)
                                                 .withProperty(BlockFence.WEST, south);

                        assertEquals(0, csblock.getSubtype(state));
                    }
                }
            }
        }
    }
}