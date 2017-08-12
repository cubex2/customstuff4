package cubex2.cs4.plugins.vanilla.block;

import cubex2.cs4.api.WrappedBlockState;
import cubex2.cs4.plugins.vanilla.ContentBlockSlab;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Optional;

public class ItemSlab extends ItemBlock
{
    private final ContentBlockSlab content;
    private final Block singleSlab;
    private final CSBlock singleSlabCS;

    public ItemSlab(Block block, ContentBlockSlab content)
    {
        super(block, content);
        singleSlab = block;
        singleSlabCS = (CSBlock) block;

        this.content = content;
    }

    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ItemStack itemstack = player.getHeldItem(hand);

        if (!itemstack.isEmpty() && player.canPlayerEdit(pos.offset(facing), facing, itemstack))
        {
            int subtype = getMetadata(itemstack);
            IBlockState currentState = worldIn.getBlockState(pos);

            if (currentState.getBlock() == singleSlab)
            {
                int subtype1 = this.singleSlabCS.getSubtype(currentState);
                net.minecraft.block.BlockSlab.EnumBlockHalf half = currentState.getValue(net.minecraft.block.BlockSlab.HALF);

                if ((facing == EnumFacing.UP && half == net.minecraft.block.BlockSlab.EnumBlockHalf.BOTTOM || facing == EnumFacing.DOWN && half == net.minecraft.block.BlockSlab.EnumBlockHalf.TOP)
                    && subtype1 == subtype)
                {
                    IBlockState stateDouble = this.makeState(subtype1);
                    AxisAlignedBB axisalignedbb = stateDouble == null ? Block.NULL_AABB : stateDouble.getCollisionBoundingBox(worldIn, pos);

                    if (stateDouble != null && axisalignedbb != Block.NULL_AABB && worldIn.checkNoEntityCollision(axisalignedbb.offset(pos)) && worldIn.setBlockState(pos, stateDouble, 11))
                    {
                        SoundType soundtype = stateDouble.getBlock().getSoundType(stateDouble, worldIn, pos, player);
                        worldIn.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                        itemstack.shrink(1);
                    }

                    return EnumActionResult.SUCCESS;
                }
            }

            return this.tryPlace(player, itemstack, worldIn, pos.offset(facing), subtype) ? EnumActionResult.SUCCESS : super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
        } else
        {
            return EnumActionResult.FAIL;
        }
    }

    @SideOnly(Side.CLIENT)
    public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side, EntityPlayer player, ItemStack stack)
    {
        BlockPos blockpos = pos;
        int subtype = getMetadata(stack);
        IBlockState clickedState = worldIn.getBlockState(pos);

        if (clickedState.getBlock() == this.singleSlab)
        {
            boolean flag = clickedState.getValue(net.minecraft.block.BlockSlab.HALF) == net.minecraft.block.BlockSlab.EnumBlockHalf.TOP;

            if ((side == EnumFacing.UP && !flag || side == EnumFacing.DOWN && flag) && subtype == singleSlabCS.getSubtype(clickedState))
            {
                return true;
            }
        }

        pos = pos.offset(side);
        IBlockState existingState = worldIn.getBlockState(pos);
        return existingState.getBlock() == this.singleSlab && subtype == singleSlabCS.getSubtype(existingState) || super.canPlaceBlockOnSide(worldIn, blockpos, side, player, stack);
    }

    private boolean tryPlace(EntityPlayer player, ItemStack stack, World worldIn, BlockPos pos, int subtype)
    {
        IBlockState iblockstate = worldIn.getBlockState(pos);

        if (iblockstate.getBlock() == this.singleSlab)
        {
            int subtype1 = singleSlabCS.getSubtype(iblockstate);

            if (subtype1 == subtype)
            {
                IBlockState stateDouble = this.makeState(subtype1);
                AxisAlignedBB axisalignedbb = stateDouble == null ? Block.NULL_AABB : stateDouble.getCollisionBoundingBox(worldIn, pos);

                if (stateDouble != null && axisalignedbb != Block.NULL_AABB && worldIn.checkNoEntityCollision(axisalignedbb.offset(pos)) && worldIn.setBlockState(pos, stateDouble, 11))
                {
                    SoundType soundtype = stateDouble.getBlock().getSoundType(stateDouble, worldIn, pos, player);
                    worldIn.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                    stack.shrink(1);
                }

                return true;
            }
        }

        return false;
    }

    @Nullable
    private IBlockState makeState(int subtype)
    {
        Optional<WrappedBlockState> state = content.doubleSlab.get(subtype);
        return state.map(WrappedBlockState::createState).orElse(null);
    }
}
