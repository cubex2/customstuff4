package cubex2.cs4.plugins.vanilla.block;

import cubex2.cs4.plugins.vanilla.ContentBlockSimple;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

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
        subtypeTmp = PropertyEnum.create("subtype", EnumSubtype.class, getUsedSubtypes(content));

        return material;
    }

    private static Collection<EnumSubtype> getUsedSubtypes(ContentBlockSimple content)
    {
        return Arrays.stream(content.subtypes)
                     .mapToObj(meta -> EnumSubtype.values()[meta])
                     .collect(Collectors.toList());
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
