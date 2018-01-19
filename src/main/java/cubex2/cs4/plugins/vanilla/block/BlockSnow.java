package cubex2.cs4.plugins.vanilla.block;

import cubex2.cs4.plugins.vanilla.BlockDrop;
import cubex2.cs4.plugins.vanilla.ContentBlockSnow;
import cubex2.cs4.util.ItemHelper;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Optional;
import java.util.Random;

public class BlockSnow extends net.minecraft.block.BlockSnow implements CSBlock<ContentBlockSnow>
{
    private final ContentBlockSnow content;

    public BlockSnow(Material material, ContentBlockSnow content)
    {
        this.content = content;
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
        if (content.snowball != null)
        {
            ItemStack stack = content.snowball.getItemStack().copy();
            stack.setCount((state.getValue(LAYERS) + 1) * stack.getCount());

            drops.add(stack);
        }

        Optional<BlockDrop[]> blockDrops = getContent().drop.get(getSubtype(state));
        if (blockDrops.isPresent())
        {
            drops.addAll(ItemHelper.getDroppedStacks(blockDrops.get()));
        }
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        if (worldIn.getLightFor(EnumSkyBlock.BLOCK, pos) > content.maxLight)
        {
            worldIn.setBlockToAir(pos);
        }
    }

    @Override
    public int getSubtype(IBlockState state)
    {
        return 0;
    }

    @Override
    public ContentBlockSnow getContent()
    {
        return content;
    }
}
