package cubex2.cs4.plugins.vanilla.block;

import cubex2.cs4.plugins.vanilla.ContentBlockOrientableVertical;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Bootstrap;
import net.minecraft.util.EnumFacing;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BlockOrientableVerticalTest
{
    @BeforeAll
    public static void setUp()
    {
        Bootstrap.register();
    }

    @Test
    public void test_getSubtype()
    {
        ContentBlockOrientableVertical content = new ContentBlockOrientableVertical();
        content.id = "test_getSubtype";

        BlockOrientable block = (BlockOrientable) content.createBlock();

        for (EnumFacing facing : BlockOrientableVertical.FACING.getAllowedValues())
        {
            IBlockState state = block.getDefaultState()
                                     .withProperty(BlockOrientableVertical.FACING, facing);

            assertEquals(0, block.getSubtype(state));
        }
    }
}