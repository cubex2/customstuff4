package cubex2.cs4.plugins.vanilla.block;

import cubex2.cs4.plugins.vanilla.ContentBlockFenceGate;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.init.Bootstrap;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.assertEquals;

public class BlockFenceGateTest
{
    @BeforeClass
    public static void setUp()
    {
        Bootstrap.register();
    }

    //todo what is beng tested?
    @Test
    @SuppressWarnings("unchecked")
    public void testProperties()
    {
        ContentBlockFenceGate content = new ContentBlockFenceGate();
        content.id = "test_getSubtype";

        Block block = content.createBlock();
        Collection<IProperty<?>> properties = block.getBlockState().getProperties();
        assertEquals(4, properties.size());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void test_getSubtype()
    {
        ContentBlockFenceGate content = new ContentBlockFenceGate();
        content.id = "test_getSubtype";

        Block block = content.createBlock();
        CSBlock<ContentBlockFenceGate> csblock = (CSBlock<ContentBlockFenceGate>) block;
        assertEquals(0, csblock.getSubtype(block.getDefaultState()));
    }
}