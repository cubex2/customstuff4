package cubex2.cs4.plugins.vanilla.block;

import cubex2.cs4.plugins.vanilla.ContentBlockSimple;
import cubex2.cs4.util.ItemHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockSimple extends Block
{
    private final ContentBlockSimple content;

    public BlockSimple(Material material, ContentBlockSimple content)
    {
        super(material);

        this.content = content;
    }

    @Override
    public float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos)
    {
        return content.hardness.get(getMetaFromState(blockState)).orElse(1f);
    }

    @Override
    public void getSubBlocks(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> list)
    {
        list.addAll(ItemHelper.createSubItems(itemIn, tab, content.creativeTab, content.subtypes));
    }
}
