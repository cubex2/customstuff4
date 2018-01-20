package cubex2.cs4.plugins.vanilla.block;

import cubex2.cs4.plugins.vanilla.ContentBlockCrops;
import cubex2.cs4.util.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Bootstrap;
import net.minecraft.util.math.AxisAlignedBB;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class BlockCropsTest
{
    @BeforeClass
    public static void setUp()
    {
        Bootstrap.register();
    }

    @Test
    public void testProperties()
    {
        ContentBlockCrops content = new ContentBlockCrops();
        content.id = "test_properties";
        content.maxAge = 9;

        Block block = content.createBlock();
        Collection<IProperty<?>> properties = block.getBlockState().getProperties();
        assertEquals(1, properties.size());
        assertSame(properties.iterator().next(), BlockHelper.getCropAgeProperty(9));
    }

    @Test
    public void test_getSubtype()
    {
        ContentBlockCrops content = new ContentBlockCrops();
        content.id = "test_getSubtype";
        content.maxAge = 9;

        Block block = content.createBlock();
        CSBlock csBlock = (CSBlock) block;

        for (Integer age : BlockHelper.getCropAgeProperty(9).getAllowedValues())
        {
            IBlockState state = block.getDefaultState()
                                     .withProperty(BlockHelper.getCropAgeProperty(9), age);

            assertEquals(0, csBlock.getSubtype(state));
        }
    }

    @Test
    public void test_ageProperty()
    {
        ContentBlockCrops content = new ContentBlockCrops();
        content.id = "test_getSubtype";

        Block block = content.createBlock();

        // this use getAgeProperty which is protected
        block.getMetaFromState(block.getDefaultState());
    }

    @Test
    public void test_defaultBounds()
    {
        ContentBlockCrops content = new ContentBlockCrops();
        content.id = "test_getSubtype";
        content.maxAge = 9;

        Block block = content.createBlock();

        AxisAlignedBB box = block.getBoundingBox(block.getDefaultState().withProperty(BlockHelper.getCropAgeProperty(9), 0), null, null);
        assertEquals(0.1, box.maxY, 0.001);
    }
}