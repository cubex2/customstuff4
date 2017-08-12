package cubex2.cs4.plugins.vanilla.block;

import cubex2.cs4.plugins.vanilla.ContentBlockOrientableDirectional;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Bootstrap;
import net.minecraft.util.EnumFacing;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BlockOrientableDirectionalWithSubtypesTest
{
    @BeforeClass
    public static void setUp() throws Exception
    {
        Bootstrap.register();
    }

    @Test
    public void test_getSubtype() throws Exception
    {
        ContentBlockOrientableDirectional content = new ContentBlockOrientableDirectional();
        content.subtypes = new int[] {0, 1};
        content.id = "test_getSubtype";

        BlockOrientableWithSubtypes block = (BlockOrientableWithSubtypes) content.createBlock();
        for (int subtype = 0; subtype < 2; subtype++)
        {
            for (EnumFacing facing : BlockOrientableDirectionalWithSubtypes.FACING.getAllowedValues())
            {
                IBlockState state = block.getDefaultState()
                                         .withProperty(BlockOrientableDirectionalWithSubtypes.FACING, facing)
                                         .withProperty(block.subtype, EnumSubtype.values()[subtype]);

                assertEquals(subtype, block.getSubtype(state));
            }
        }

    }
}