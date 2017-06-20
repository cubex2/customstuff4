package cubex2.cs4.plugins.vanilla.block;

import cubex2.cs4.plugins.vanilla.ContentBlockSlab;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collections;

public abstract class BlockSlab extends Block implements CSBlock<ContentBlockSlab>
{
    public static final PropertyEnum<net.minecraft.block.BlockSlab.EnumBlockHalf> HALF = PropertyEnum.<net.minecraft.block.BlockSlab.EnumBlockHalf>create("half", net.minecraft.block.BlockSlab.EnumBlockHalf.class);
    protected static final AxisAlignedBB AABB_BOTTOM_HALF = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);
    protected static final AxisAlignedBB AABB_TOP_HALF = new AxisAlignedBB(0.0D, 0.5D, 0.0D, 1.0D, 1.0D, 1.0D);

    private final ContentBlockSlab content;
    protected StateMetaMapper<BlockSlab> mapper;

    public BlockSlab(Material material, ContentBlockSlab content)
    {
        super(material, MapColor.AIR); // that map color is being overridden

        this.content = content;
        mapper = StateMetaMapper.create(Collections.singleton(HALF));

        useNeighborBrightness = true;
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return mapper.getMetaFromState(state);
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return mapper.getStateFromMeta(this, meta);
    }

    @Override
    protected boolean canSilkHarvest()
    {
        return false;
    }

    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return (state.getValue(HALF) == net.minecraft.block.BlockSlab.EnumBlockHalf.TOP ? AABB_TOP_HALF : AABB_BOTTOM_HALF);
    }

    /**
     * Checks if an IBlockState represents a block that is opaque and a full cube.
     */
    public boolean isFullyOpaque(IBlockState state)
    {
        return state.getValue(HALF) == net.minecraft.block.BlockSlab.EnumBlockHalf.TOP;
    }

    @Override
    public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face)
    {
        if (net.minecraftforge.common.ForgeModContainer.disableStairSlabCulling)
            return super.doesSideBlockRendering(state, world, pos, face);

        if (state.isOpaqueCube())
            return true;

        net.minecraft.block.BlockSlab.EnumBlockHalf side = state.getValue(HALF);
        return (side == net.minecraft.block.BlockSlab.EnumBlockHalf.TOP && face == EnumFacing.UP) || (side == net.minecraft.block.BlockSlab.EnumBlockHalf.BOTTOM && face == EnumFacing.DOWN);
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        IBlockState iblockstate = getDefaultState().withProperty(HALF, net.minecraft.block.BlockSlab.EnumBlockHalf.BOTTOM);
        return (facing != EnumFacing.DOWN && (facing == EnumFacing.UP || (double) hitY <= 0.5D) ? iblockstate : iblockstate.withProperty(HALF, net.minecraft.block.BlockSlab.EnumBlockHalf.TOP));
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        if (side != EnumFacing.UP && side != EnumFacing.DOWN && !super.shouldSideBeRendered(blockState, blockAccess, pos, side))
        {
            return false;
        }

        return super.shouldSideBeRendered(blockState, blockAccess, pos, side);
    }

    @Deprecated
    @SideOnly(Side.CLIENT)
    public int getPackedLightmapCoords(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        int i = source.getCombinedLight(pos, state.getLightValue(source, pos));

        if (i == 0 && (state.getBlock() instanceof net.minecraft.block.BlockSlab || state.getBlock() instanceof BlockSlab))
        {
            pos = pos.down();
            state = source.getBlockState(pos);
            return source.getCombinedLight(pos, state.getLightValue(source, pos));
        } else
        {
            return i;
        }
    }

    @Override
    public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side)
    {
        IBlockState state = this.getActualState(base_state, world, pos);
        return base_state.isFullBlock()
               || (state.getValue(net.minecraft.block.BlockSlab.HALF) == net.minecraft.block.BlockSlab.EnumBlockHalf.TOP && side == EnumFacing.UP)
               || (state.getValue(net.minecraft.block.BlockSlab.HALF) == net.minecraft.block.BlockSlab.EnumBlockHalf.BOTTOM && side == EnumFacing.DOWN);
    }

    @Override
    public int getSubtype(IBlockState state)
    {
        return 0;
    }

    @Override
    public ContentBlockSlab getContent()
    {
        return content;
    }

    @Override
    public int[] getSubtypes()
    {
        return content.subtypes;
    }

    @Override
    public IProperty[] getProperties()
    {
        return new IProperty[] {HALF};
    }
}
