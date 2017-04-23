package cubex2.cs4.plugins.vanilla.block;

import com.google.common.collect.Lists;
import cubex2.cs4.plugins.vanilla.ContentBlockSlab;
import cubex2.cs4.util.BlockHelper;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockSlabWithSubtypes extends BlockSlab
{
    private final PropertyEnum<EnumSubtype> subtype;

    public BlockSlabWithSubtypes(Material material, ContentBlockSlab content)
    {
        super(material, content);

        subtype = BlockHelper.getSubtypeProperty(content.subtypes);
        mapper = StateMetaMapper.create(Lists.newArrayList(subtype, HALF));

        setDefaultState(getDefaultState().withProperty(subtype, EnumSubtype.SUBTYPE0));
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return super.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer)
                    .withProperty(subtype, EnumSubtype.values()[meta]);
    }

    @Override
    public int getSubtype(IBlockState state)
    {
        return state.getValue(subtype).ordinal();
    }
}
