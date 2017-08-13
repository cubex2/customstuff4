package cubex2.cs4.plugins.vanilla.block;

import cubex2.cs4.plugins.vanilla.ContentBlockFence;
import cubex2.cs4.util.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Bootstrap;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BlockFenceWithSubtypesTest
{
    @BeforeClass
    public static void setUp() throws Exception
    {
        Bootstrap.register();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void test_getSubtype() throws Exception
    {
        ContentBlockFence content = new ContentBlockFence();
        content.subtypes = new int[] {0, 1, 2, 3, 4, 5, 6, 7};
        content.id = "test_getSubtype";

        Block block = content.createBlock();
        CSBlock<ContentBlockFence> csblock = (CSBlock<ContentBlockFence>) block;
        for (int subtype = 0; subtype < 8; subtype++)
        {
            IBlockState state = block.getDefaultState()
                                     .withProperty(BlockFence.NORTH, false)
                                     .withProperty(BlockFence.SOUTH, true)
                                     .withProperty(BlockHelper.getSubtypeProperty(content.subtypes), EnumSubtype.values()[subtype]);

            assertEquals(subtype, csblock.getSubtype(state));
        }

    }
}