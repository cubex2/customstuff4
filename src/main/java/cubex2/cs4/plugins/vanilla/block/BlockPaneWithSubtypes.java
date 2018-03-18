package cubex2.cs4.plugins.vanilla.block;

import cubex2.cs4.plugins.vanilla.ContentBlockPane;
import cubex2.cs4.util.BlockHelper;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;

import java.util.Collections;

public class BlockPaneWithSubtypes extends BlockPane
{
    private final PropertyEnum<EnumSubtype> subtype;
    private final StateMetaMapper<BlockPane> mapper;

    public BlockPaneWithSubtypes(Material materialIn, ContentBlockPane content)
    {
        super(materialIn, content);

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
