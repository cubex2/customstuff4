package cubex2.cs4.plugins.vanilla.block;

import cubex2.cs4.plugins.vanilla.ContentBlockBaseWithSubtypes;
import cubex2.cs4.plugins.vanilla.ContentBlockOrientable;
import cubex2.cs4.util.BlockHelper;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
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
    public final IBlockState getStateFromMeta(int meta)
    {
        int numFacingBits = getNumFacingBits();
        int facingMask = (1 << numFacingBits) - 1;
        int subtypeMask = 15 - facingMask;

        return this.getDefaultState()
                   .withProperty(getFacingProperty(), getFacingFromIndex(meta & facingMask))
                   .withProperty(subtype, EnumSubtype.values()[(meta & subtypeMask) >> numFacingBits]);
    }

    protected EnumFacing getFacingFromIndex(int index)
    {
        return EnumFacing.getFront(index);
    }

    @Override
    public final int getMetaFromState(IBlockState state)
    {
        int facingBits = getIndexFromFacing(state.getValue(getFacingProperty()));
        int subtypeBits = state.getValue(subtype).ordinal() << getNumFacingBits();

        return facingBits | subtypeBits;
    }

    protected abstract int getNumFacingBits();

    protected int getIndexFromFacing(EnumFacing facing)
    {
        return facing.getIndex();
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return state.getValue(subtype).ordinal();
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        ContentBlockBaseWithSubtypes.getActiveContent()
                                    .ifPresent(c -> subtype = BlockHelper.getSubtypeProperty(c.subtypes));

        return new BlockStateContainer(this, subtype, getFacingProperty());
    }
}
