package cubex2.cs4.plugins.vanilla.block;

import cubex2.cs4.plugins.vanilla.Attribute;
import cubex2.cs4.plugins.vanilla.BlockDrop;
import cubex2.cs4.plugins.vanilla.ContentBlockSnow;
import cubex2.cs4.plugins.vanilla.WrappedItemStackConstant;
import cubex2.cs4.util.IntRange;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Bootstrap;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class BlockSnowTest
{
    @BeforeClass
    public static void setUp()
    {
        Bootstrap.register();
    }

    @Test
    public void testProperties()
    {
        ContentBlockSnow content = new ContentBlockSnow();
        content.id = "test_getSubtype";

        Block block = content.createBlock();
        Collection<IProperty<?>> properties = block.getBlockState().getProperties();
        assertEquals(1, properties.size());
        assertSame(properties.iterator().next(), net.minecraft.block.BlockSnow.LAYERS);
    }

    @Test
    public void test_getSubtype()
    {
        ContentBlockSnow content = new ContentBlockSnow();
        content.id = "test_getSubtype";

        Block block = content.createBlock();
        CSBlock csBlock = (CSBlock) block;

        for (Integer layer : net.minecraft.block.BlockSnow.LAYERS.getAllowedValues())
        {
            IBlockState state = block.getDefaultState()
                                     .withProperty(net.minecraft.block.BlockSnow.LAYERS, layer);

            assertEquals(0, csBlock.getSubtype(state));
        }
    }

    @Test
    public void test_getDrops()
    {
        ContentBlockSnow content = new ContentBlockSnow();
        content.id = "test_getDrops";
        content.snowball = new WrappedItemStackConstant(new ItemStack(Items.APPLE, 3));
        content.drop = Attribute.constant(new BlockDrop[] {new BlockDrop(new WrappedItemStackConstant(new ItemStack(Items.STICK)), IntRange.create(2, 2))});

        Block block = content.createBlock();

        NonNullList<ItemStack> drops = NonNullList.create();
        block.getDrops(drops, null, null, block.getDefaultState().withProperty(BlockSnow.LAYERS, 5), 0);
        ItemStack drop1 = drops.get(0);
        ItemStack drop2 = drops.get(1);

        assertEquals(2, drops.size());

        assertSame(Items.APPLE, drop1.getItem());
        assertEquals(18, drop1.getCount());

        assertSame(Items.STICK, drop2.getItem());
        assertEquals(2, drop2.getCount());
    }
}