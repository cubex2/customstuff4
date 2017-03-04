package cubex2.cs4.plugins.vanilla.block;

import cubex2.cs4.plugins.vanilla.ContentBlockBaseWithSubtypes;
import cubex2.cs4.plugins.vanilla.ContentBlockSimple;
import cubex2.cs4.util.BlockHelper;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;

public class BlockSimpleWithSubtypes extends BlockSimple
{
    private PropertyEnum<EnumSubtype> subtype;

    public BlockSimpleWithSubtypes(Material material, ContentBlockSimple content)
    {
        super(material, content);

        subtype = BlockHelper.getSubtypeProperty(content.subtypes);

        setDefaultState(blockState.getBaseState()
                                  .withProperty(subtype, EnumSubtype.SUBTYPE0));
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
        ContentBlockBaseWithSubtypes.getActiveContent()
                                    .ifPresent(c -> subtype = BlockHelper.getSubtypeProperty(c.subtypes));

        return new BlockStateContainer(this, subtype);
    }
}
