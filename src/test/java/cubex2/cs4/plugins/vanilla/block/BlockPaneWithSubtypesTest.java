package cubex2.cs4.plugins.vanilla.block;

import cubex2.cs4.plugins.vanilla.ContentBlockPane;
import cubex2.cs4.plugins.vanilla.ContentBlockWall;
import cubex2.cs4.util.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Bootstrap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BlockPaneWithSubtypesTest
{
    @BeforeAll
    public static void setUp()
    {
        Bootstrap.register();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testProperties()
    {
        ContentBlockPane content = new ContentBlockPane();
        content.subtypes = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        content.id = "test_getSubtype";

        Block block = content.createBlock();
        Collection<IProperty<?>> properties = block.getBlockState().getProperties();
        assertEquals(5, properties.size());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void test_getSubtype()
    {
        ContentBlockPane content = new ContentBlockPane();
        content.subtypes = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        content.id = "test_getSubtype";

        Block block = content.createBlock();
        CSBlock<ContentBlockPane> csblock = (CSBlock<ContentBlockPane>) block;
        for (int subtype = 0; subtype < 16; subtype++)
        {
            IBlockState state = block.getDefaultState()
                                     .withProperty(BlockHelper.getSubtypeProperty(content.subtypes), EnumSubtype.values()[subtype]);

            assertEquals(subtype, csblock.getSubtype(state));
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void test_getSubtypes()
    {
        ContentBlockWall content = new ContentBlockWall();
        content.subtypes = new int[] {0, 1};
        content.id = "test_getSubtype";

        Block block = content.createBlock();
        CSBlock<ContentBlockWall> csblock = (CSBlock<ContentBlockWall>) block;

        assertEquals(2, csblock.getSubtypes().length);
    }
}