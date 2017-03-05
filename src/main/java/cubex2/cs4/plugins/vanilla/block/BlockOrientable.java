package cubex2.cs4.plugins.vanilla.block;

import cubex2.cs4.plugins.vanilla.ContentBlockOrientable;
import cubex2.cs4.util.BlockHelper;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;

public abstract class BlockOrientable extends BlockSimple
{
    protected final ContentBlockOrientable content;

    public BlockOrientable(Material material, ContentBlockOrientable content)
    {
        super(material, content);
        this.content = content;
    }

    protected abstract PropertyDirection getFacingProperty();

    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot)
    {
        return state.withProperty(getFacingProperty(), rot.rotate(state.getValue(getFacingProperty())));
    }

    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
    {
        return state.withRotation(mirrorIn.toRotation(state.getValue(getFacingProperty())));
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, getFacingProperty());
    }

    protected final EnumFacing getVerticalFacingForPlacement(BlockPos pos, EnumFacing facing, int meta, EntityLivingBase placer)
    {
        if (content.faceBySide.get(meta).orElse(false) && facing.getAxis().isVertical())
            return facing;
        else
            return BlockHelper.getVerticalFacingFromEntity(pos, placer);
    }

    protected final EnumFacing getHorizontalFacingForPlacement(BlockPos pos, EnumFacing facing, int meta, EntityLivingBase placer)
    {
        if (content.faceBySide.get(meta).orElse(false) && facing.getAxis().isHorizontal())
            return facing;
        else
            return placer.getHorizontalFacing().getOpposite();
    }

    protected final EnumFacing getDirectionalFacingForPlacement(BlockPos pos, EnumFacing facing, int meta, EntityLivingBase placer)
    {
        if (content.faceBySide.get(meta).orElse(false))
            return facing;
        else
            return EnumFacing.getDirectionFromEntityLiving(pos, placer);
    }
}
