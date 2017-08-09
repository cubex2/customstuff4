package cubex2.cs4.plugins.vanilla.block;

import cubex2.cs4.plugins.vanilla.ContentBlockOrientableHorizontal;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class BlockOrientableHorizontal extends BlockOrientable
{
    public static final PropertyDirection FACING = BlockHorizontal.FACING;

    public BlockOrientableHorizontal(Material material, ContentBlockOrientableHorizontal content)
    {
        super(material, content);

        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
    }

    @Override
    protected PropertyDirection getFacingProperty()
    {
        return FACING;
    }

    @Override
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(FACING, getHorizontalFacingForPlacement(pos, facing, meta, placer));
    }
}
