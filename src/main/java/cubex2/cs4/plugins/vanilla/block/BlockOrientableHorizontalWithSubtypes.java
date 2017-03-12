package cubex2.cs4.plugins.vanilla.block;

import cubex2.cs4.plugins.vanilla.ContentBlockOrientableHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public abstract class BlockOrientableHorizontalWithSubtypes extends BlockOrientableWithSubtypes
{
    public static final PropertyDirection FACING = BlockOrientableHorizontal.FACING;

    public BlockOrientableHorizontalWithSubtypes(Material material, ContentBlockOrientableHorizontal content)
    {
        super(material, content);

        setDefaultState(blockState.getBaseState().withProperty(subtype, EnumSubtype.SUBTYPE0)
                                  .withProperty(FACING, EnumFacing.NORTH));
    }

    @Override
    protected PropertyDirection getFacingProperty()
    {
        return FACING;
    }

    @Override
    protected EnumFacing getFacingForPlacement(BlockPos pos, EnumFacing facing, int meta, EntityLivingBase placer)
    {
        return getHorizontalFacingForPlacement(pos, facing, meta, placer);
    }
}
