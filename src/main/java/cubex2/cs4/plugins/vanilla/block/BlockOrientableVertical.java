package cubex2.cs4.plugins.vanilla.block;

import cubex2.cs4.plugins.vanilla.ContentBlockOrientable;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class BlockOrientableVertical extends BlockOrientable
{
    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.VERTICAL);

    public BlockOrientableVertical(Material material, ContentBlockOrientable content)
    {
        super(material, content);

        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.UP));
    }

    @Override
    protected PropertyDirection getFacingProperty()
    {
        return FACING;
    }

    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(FACING, getVerticalFacingForPlacement(pos, facing, meta, placer));
    }
}
