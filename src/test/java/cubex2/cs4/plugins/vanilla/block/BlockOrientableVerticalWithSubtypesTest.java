package cubex2.cs4.plugins.vanilla.block;

import cubex2.cs4.plugins.vanilla.ContentBlockOrientableVertical;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Bootstrap;
import net.minecraft.util.EnumFacing;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BlockOrientableVerticalWithSubtypesTest
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
        content.subtypes = new int[] {0, 1, 2, 3, 4, 5, 6, 7};
        content.id = "test_getSubtype";

        BlockOrientableWithSubtypes block = (BlockOrientableWithSubtypes) content.createBlock();
        for (int subtype = 0; subtype < 8; subtype++)
        {
            for (EnumFacing facing : BlockOrientableVerticalWithSubtypes.FACING.getAllowedValues())
            {
                IBlockState state = block.getDefaultState()
                                         .withProperty(BlockOrientableVerticalWithSubtypes.FACING, facing)
                                         .withProperty(block.subtype, EnumSubtype.values()[subtype]);

                assertEquals(subtype, block.getSubtype(state));
            }
        }

    }
}