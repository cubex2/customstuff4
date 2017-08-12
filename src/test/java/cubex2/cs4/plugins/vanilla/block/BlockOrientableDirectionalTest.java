package cubex2.cs4.plugins.vanilla.block;

import cubex2.cs4.plugins.vanilla.ContentBlockOrientableDirectional;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Bootstrap;
import net.minecraft.util.EnumFacing;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BlockOrientableDirectionalTest
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
        content.id = "test_getSubtype";

        BlockOrientable block = (BlockOrientable) content.createBlock();

        for (EnumFacing facing : BlockOrientableDirectional.FACING.getAllowedValues())
        {
            IBlockState state = block.getDefaultState()
                                     .withProperty(BlockOrientableDirectional.FACING, facing);

            assertEquals(0, block.getSubtype(state));
        }
    }
}