package cubex2.cs4.plugins.vanilla.block;

import cubex2.cs4.plugins.vanilla.ContentBlockCarpet;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static cubex2.cs4.plugins.vanilla.block.BlockMixin.DEFAULT_AABB_MARKER;

public abstract class BlockCarpet extends Block implements CSBlock<ContentBlockCarpet>
{
    protected static final AxisAlignedBB CARPET_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.0625D, 1.0D);

    private final ContentBlockCarpet content;

    public BlockCarpet(Material material, ContentBlockCarpet content)
    {
        super(material, MapColor.AIR);

        this.content = content;

        setTickRandomly(true);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        AxisAlignedBB bounds = getContent().bounds.get(getSubtype(state)).orElse(DEFAULT_AABB_MARKER);
        return bounds == DEFAULT_AABB_MARKER ? CARPET_AABB : bounds;
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        return super.canPlaceBlockAt(worldIn, pos) && canBlockStay(worldIn, pos);
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        checkForDrop(worldIn, pos, state);
    }

    private boolean checkForDrop(World worldIn, BlockPos pos, IBlockState state)
    {
        if (!canBlockStay(worldIn, pos))
        {
            dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockToAir(pos);
            return false;
        } else
        {
            return true;
        }
    }

    private boolean canBlockStay(World worldIn, BlockPos pos)
    {
        return !worldIn.isAirBlock(pos.down());
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState state, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        if (side == EnumFacing.UP)
        {
            return true;
        } else
        {
            return blockAccess.getBlockState(pos.offset(side)).getBlock() == this || super.shouldSideBeRendered(state, blockAccess, pos, side);
        }
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess blockAccess, IBlockState state, BlockPos pos, EnumFacing side)
    {
        return side == EnumFacing.DOWN ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
    }

    @Override
    public int getSubtype(IBlockState state)
    {
        return getMetaFromState(state);
    }

    @Override
    public ContentBlockCarpet getContent()
    {
        return content;
    }

    @Override
    public int[] getSubtypes()
    {
        return content.subtypes;
    }
}
