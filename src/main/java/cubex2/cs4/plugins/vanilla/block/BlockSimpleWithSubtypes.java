package cubex2.cs4.plugins.vanilla.block;

import cubex2.cs4.plugins.vanilla.ContentBlockSimple;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;

public class BlockSimpleWithSubtypes extends BlockSimple
{
    private PropertyEnum<EnumSubtype> subtype;
    private static PropertyEnum<EnumSubtype> subtypeTmp;

    public BlockSimpleWithSubtypes(Material material, ContentBlockSimple content)
    {
        super(setTmpSubtype(material, content), content);

        subtype = subtypeTmp;
        subtypeTmp = null;

        setDefaultState(blockState.getBaseState()
                                  .withProperty(subtype, EnumSubtype.SUBTYPE0));
    }

    private static Material setTmpSubtype(Material material, ContentBlockSimple content)
    {
        // Hacky, but it's the only way to have a non-static property
        subtypeTmp = PropertyEnum.create("subtype", EnumSubtype.class, EnumSubtype.getValues(content.subtypes));

        return material;
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return getMetaFromState(state);
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return getDefaultState().withProperty(subtype, EnumSubtype.values()[meta]);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(subtype).ordinal();
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, subtype == null ? subtypeTmp : subtype);
    }
}
