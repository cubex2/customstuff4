package cubex2.cs4.plugins.vanilla.block;

import cubex2.cs4.plugins.vanilla.ContentBlockFluid;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Bootstrap;
import net.minecraftforge.common.property.ExtendedBlockState;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BlockFluidTest
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
        ContentBlockFluid content = new ContentBlockFluid();
        content.id = "test_getSubtype";

        Block block = content.createBlock();
        ExtendedBlockState state = (ExtendedBlockState) block.getBlockState();
        Collection<IProperty<?>> properties = state.getProperties();
        assertEquals(1, properties.size());
        assertEquals(5, state.getUnlistedProperties().size());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void test_getSubtype()
    {
        ContentBlockFluid content = new ContentBlockFluid();
        content.id = "test_getSubtype";

        Block block = content.createBlock();
        CSBlock<ContentBlockFluid> csblock = (CSBlock<ContentBlockFluid>) block;
        for (Integer level : BlockFluid.LEVEL.getAllowedValues())
        {
            IBlockState state = block.getDefaultState()
                                     .withProperty(BlockFluid.LEVEL, level);

            assertEquals(0, csblock.getSubtype(state));
        }
    }
}