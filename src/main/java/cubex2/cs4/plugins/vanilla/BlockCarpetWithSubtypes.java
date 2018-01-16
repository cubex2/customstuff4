package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.plugins.vanilla.block.BlockCarpet;
import cubex2.cs4.plugins.vanilla.block.EnumSubtype;
import cubex2.cs4.plugins.vanilla.block.StateMetaMapper;
import cubex2.cs4.util.BlockHelper;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;

import java.util.Collections;

public class BlockCarpetWithSubtypes extends BlockCarpet
{
    private final PropertyEnum<EnumSubtype> subtype;
    private final StateMetaMapper<BlockCarpet> mapper;

    public BlockCarpetWithSubtypes(Material material, ContentBlockCarpet content)
    {
        super(material, content);

        subtype = BlockHelper.getSubtypeProperty(content.subtypes);
        mapper = StateMetaMapper.create(Collections.singleton(subtype));

        setDefaultState(getDefaultState().withProperty(subtype, EnumSubtype.SUBTYPE0));
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
}
