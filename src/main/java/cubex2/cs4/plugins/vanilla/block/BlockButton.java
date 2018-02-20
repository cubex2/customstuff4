package cubex2.cs4.plugins.vanilla.block;

import cubex2.cs4.plugins.vanilla.ContentBlockButton;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class BlockButton extends BlockDirectional implements CSBlock<ContentBlockButton>
{
    static final PropertyBool POWERED = PropertyBool.create("powered");
    static final AxisAlignedBB AABB_DOWN_OFF = new AxisAlignedBB(0.3125D, 0.875D, 0.375D, 0.6875D, 1.0D, 0.625D);
    static final AxisAlignedBB AABB_UP_OFF = new AxisAlignedBB(0.3125D, 0.0D, 0.375D, 0.6875D, 0.125D, 0.625D);
    static final AxisAlignedBB AABB_NORTH_OFF = new AxisAlignedBB(0.3125D, 0.375D, 0.875D, 0.6875D, 0.625D, 1.0D);
    static final AxisAlignedBB AABB_SOUTH_OFF = new AxisAlignedBB(0.3125D, 0.375D, 0.0D, 0.6875D, 0.625D, 0.125D);
    static final AxisAlignedBB AABB_WEST_OFF = new AxisAlignedBB(0.875D, 0.375D, 0.3125D, 1.0D, 0.625D, 0.6875D);
    static final AxisAlignedBB AABB_EAST_OFF = new AxisAlignedBB(0.0D, 0.375D, 0.3125D, 0.125D, 0.625D, 0.6875D);
    static final AxisAlignedBB AABB_DOWN_ON = new AxisAlignedBB(0.3125D, 0.9375D, 0.375D, 0.6875D, 1.0D, 0.625D);
    static final AxisAlignedBB AABB_UP_ON = new AxisAlignedBB(0.3125D, 0.0D, 0.375D, 0.6875D, 0.0625D, 0.625D);
    static final AxisAlignedBB AABB_NORTH_ON = new AxisAlignedBB(0.3125D, 0.375D, 0.9375D, 0.6875D, 0.625D, 1.0D);
    static final AxisAlignedBB AABB_SOUTH_ON = new AxisAlignedBB(0.3125D, 0.375D, 0.0D, 0.6875D, 0.625D, 0.0625D);
    static final AxisAlignedBB AABB_WEST_ON = new AxisAlignedBB(0.9375D, 0.375D, 0.3125D, 1.0D, 0.625D, 0.6875D);
    static final AxisAlignedBB AABB_EAST_ON = new AxisAlignedBB(0.0D, 0.375D, 0.3125D, 0.0625D, 0.625D, 0.6875D);

    private final ContentBlockButton content;

    public BlockButton(Material material, ContentBlockButton content)
    {
        super(material);
        this.content = content;

        setDefaultState(blockState.getBaseState()
                                  .withProperty(FACING, EnumFacing.NORTH)
                                  .withProperty(POWERED, false));
        setTickRandomly(true);
    }

    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
    {
        return NULL_AABB;
    }

    @Override
    public int tickRate(World worldIn)
    {
        return content.pressedTicks;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side)
    {
        return canPlaceBlock(worldIn, pos, side);
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        for (EnumFacing enumfacing : EnumFacing.values())
        {
            if (canPlaceBlock(worldIn, pos, enumfacing))
            {
                return true;
            }
        }

        return false;
    }


    static boolean canPlaceBlock(World worldIn, BlockPos pos, EnumFacing direction)
    {
        BlockPos blockpos = pos.offset(direction.getOpposite());
        IBlockState iblockstate = worldIn.getBlockState(blockpos);
        boolean flag = iblockstate.getBlockFaceShape(worldIn, blockpos, direction) == BlockFaceShape.SOLID;
        Block block = iblockstate.getBlock();

        if (direction == EnumFacing.UP)
        {
            return iblockstate.isTopSolid() || !isExceptionBlockForAttaching(block) && flag;
        } else
        {
            return !isExceptBlockForAttachWithPiston(block) && flag;
        }
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        if (canPlaceBlock(worldIn, pos, facing))
            return this.getDefaultState()
                       .withProperty(FACING, facing)
                       .withProperty(POWERED, false);
        else
            return this.getDefaultState()
                       .withProperty(FACING, EnumFacing.DOWN)
                       .withProperty(POWERED, false);
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        if (this.checkForDrop(worldIn, pos, state) && !canPlaceBlock(worldIn, pos, state.getValue(FACING)))
        {
            this.dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockToAir(pos);
        }
    }

    private boolean checkForDrop(World worldIn, BlockPos pos, IBlockState state)
    {
        if (this.canPlaceBlockAt(worldIn, pos))
        {
            return true;
        } else
        {
            this.dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockToAir(pos);
            return false;
        }
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        EnumFacing enumfacing = state.getValue(FACING);
        boolean flag = state.getValue(POWERED);

        switch (enumfacing)
        {
            case EAST:
                return flag ? AABB_EAST_ON : AABB_EAST_OFF;
            case WEST:
                return flag ? AABB_WEST_ON : AABB_WEST_OFF;
            case SOUTH:
                return flag ? AABB_SOUTH_ON : AABB_SOUTH_OFF;
            case NORTH:
            default:
                return flag ? AABB_NORTH_ON : AABB_NORTH_OFF;
            case UP:
                return flag ? AABB_UP_ON : AABB_UP_OFF;
            case DOWN:
                return flag ? AABB_DOWN_ON : AABB_DOWN_OFF;
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (state.getValue(POWERED))
        {
            return true;
        } else
        {
            worldIn.setBlockState(pos, state.withProperty(POWERED, true), 3);
            worldIn.markBlockRangeForRenderUpdate(pos, pos);
            this.playClickSound(playerIn, worldIn, pos);
            this.notifyNeighbors(worldIn, pos, state.getValue(FACING));
            worldIn.scheduleUpdate(pos, this, this.tickRate(worldIn));
            return true;
        }
    }

    protected void playClickSound(@Nullable EntityPlayer player, World worldIn, BlockPos pos)
    {
        worldIn.playSound(player, pos, SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON, SoundCategory.BLOCKS, 0.3F, 0.6F);
    }

    protected void playReleaseSound(World worldIn, BlockPos pos)
    {
        worldIn.playSound(null, pos, SoundEvents.BLOCK_STONE_BUTTON_CLICK_OFF, SoundCategory.BLOCKS, 0.3F, 0.5F);
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        if (state.getValue(POWERED))
        {
            this.notifyNeighbors(worldIn, pos, state.getValue(FACING));
        }

        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        return blockState.getValue(POWERED) ? 15 : 0;
    }

    @Override
    public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        if (!blockState.getValue(POWERED))
        {
            return 0;
        } else
        {
            return blockState.getValue(FACING) == side ? 15 : 0;
        }
    }

    @Override
    public boolean canProvidePower(IBlockState state)
    {
        return true;
    }

    @Override
    public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random)
    {
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        if (!worldIn.isRemote)
        {
            if (state.getValue(POWERED))
            {
                if (content.triggeredByArrows)
                {
                    this.checkPressed(state, worldIn, pos);
                } else
                {
                    worldIn.setBlockState(pos, state.withProperty(POWERED, false));
                    this.notifyNeighbors(worldIn, pos, state.getValue(FACING));
                    this.playReleaseSound(worldIn, pos);
                    worldIn.markBlockRangeForRenderUpdate(pos, pos);
                }
            }
        }
    }

    @Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
    {
        if (!worldIn.isRemote)
        {
            if (content.triggeredByArrows)
            {
                if (!state.getValue(POWERED))
                {
                    checkPressed(state, worldIn, pos);
                }
            }
        }
    }

    private void checkPressed(IBlockState state, World worldIn, BlockPos pos)
    {
        List<? extends Entity> list = worldIn.<Entity>getEntitiesWithinAABB(EntityArrow.class, state.getBoundingBox(worldIn, pos).offset(pos));
        boolean flag = !list.isEmpty();
        boolean flag1 = state.getValue(POWERED);

        if (flag && !flag1)
        {
            worldIn.setBlockState(pos, state.withProperty(POWERED, true));
            this.notifyNeighbors(worldIn, pos, state.getValue(FACING));
            worldIn.markBlockRangeForRenderUpdate(pos, pos);
            this.playClickSound(null, worldIn, pos);
        }

        if (!flag && flag1)
        {
            worldIn.setBlockState(pos, state.withProperty(POWERED, false));
            this.notifyNeighbors(worldIn, pos, state.getValue(FACING));
            worldIn.markBlockRangeForRenderUpdate(pos, pos);
            this.playReleaseSound(worldIn, pos);
        }

        if (flag)
        {
            worldIn.scheduleUpdate(new BlockPos(pos), this, this.tickRate(worldIn));
        }
    }

    private void notifyNeighbors(World worldIn, BlockPos pos, EnumFacing facing)
    {
        worldIn.notifyNeighborsOfStateChange(pos, this, false);
        worldIn.notifyNeighborsOfStateChange(pos.offset(facing.getOpposite()), this, false);
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        EnumFacing enumfacing;

        switch (meta & 7)
        {
            case 0:
                enumfacing = EnumFacing.DOWN;
                break;
            case 1:
                enumfacing = EnumFacing.EAST;
                break;
            case 2:
                enumfacing = EnumFacing.WEST;
                break;
            case 3:
                enumfacing = EnumFacing.SOUTH;
                break;
            case 4:
                enumfacing = EnumFacing.NORTH;
                break;
            case 5:
            default:
                enumfacing = EnumFacing.UP;
        }

        return this.getDefaultState().withProperty(FACING, enumfacing).withProperty(POWERED, (meta & 8) > 0);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        int i;

        switch (state.getValue(FACING))
        {
            case EAST:
                i = 1;
                break;
            case WEST:
                i = 2;
                break;
            case SOUTH:
                i = 3;
                break;
            case NORTH:
                i = 4;
                break;
            case UP:
            default:
                i = 5;
                break;
            case DOWN:
                i = 0;
        }

        if (state.getValue(POWERED))
        {
            i |= 8;
        }

        return i;
    }

    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot)
    {
        return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
    {
        return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING, POWERED);
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing facing)
    {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    public int getSubtype(IBlockState state)
    {
        return 0;
    }

    @Override
    public ContentBlockButton getContent()
    {
        return content;
    }
}
