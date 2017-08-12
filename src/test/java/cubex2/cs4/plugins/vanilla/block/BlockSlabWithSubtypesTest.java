package cubex2.cs4.plugins.vanilla.block;

import cubex2.cs4.plugins.vanilla.ContentBlockSlab;
import cubex2.cs4.util.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Bootstrap;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BlockSlabWithSubtypesTest
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
        ContentBlockSlab content = new ContentBlockSlab();
        content.subtypes = new int[] {0, 1, 2, 3, 4, 5, 6, 7};
        content.id = "test_getSubtype";

        Block block = content.createBlock();
        CSBlock<ContentBlockSlab> csblock = (CSBlock<ContentBlockSlab>) block;
        for (int subtype = 0; subtype < 8; subtype++)
        {
            for (BlockSlab.EnumBlockHalf facing : BlockSlabWithSubtypes.HALF.getAllowedValues())
            {
                IBlockState state = block.getDefaultState()
                                         .withProperty(BlockSlabWithSubtypes.HALF, facing)
                                         .withProperty(BlockHelper.getSubtypeProperty(content.subtypes), EnumSubtype.values()[subtype]);

                assertEquals(subtype, csblock.getSubtype(state));
            }
        }

    }
}