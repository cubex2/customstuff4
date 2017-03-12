package cubex2.cs4.plugins.vanilla.block;

import cubex2.cs4.plugins.vanilla.ContentBlockOrientable;
import cubex2.cs4.util.BlockHelper;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

abstract class BlockOrientableWithSubtypes extends BlockOrientable
{
    protected PropertyEnum<EnumSubtype> subtype;

    BlockOrientableWithSubtypes(Material material, ContentBlockOrientable content)
    {
        super(material, content);

        subtype = BlockHelper.getSubtypeProperty(content.subtypes);
    }

    @Override
    public final IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState()
                   .withProperty(getFacingProperty(), getFacingForPlacement(pos, facing, meta, placer))
                   .withProperty(subtype, EnumSubtype.values()[meta]);
    }

    protected abstract EnumFacing getFacingForPlacement(BlockPos pos, EnumFacing facing, int meta, EntityLivingBase placer);

    @Override
    public int damageDropped(IBlockState state)
    {
        return state.getValue(subtype).ordinal();
    }
}
