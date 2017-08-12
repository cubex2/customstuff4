package cubex2.cs4.plugins.vanilla.block;

import cubex2.cs4.plugins.vanilla.ContentBlockSimple;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

public abstract class BlockSimple extends Block implements CSBlock<ContentBlockSimple>
{
    private final ContentBlockSimple content;
    private final StateMetaMapper<Block> mapper;

    public BlockSimple(Material material, ContentBlockSimple content)
    {
        super(material);

        this.content = content;
        this.mapper = StateMetaMapper.create(blockState.getProperties());
    }

    @Override
    public int getSubtype(IBlockState state)
    {
        return getMetaFromState(state);
    }

    @Override
    public int[] getSubtypes()
    {
        return content.subtypes;
    }

    @Override
    public ContentBlockSimple getContent()
    {
        return content;
    }

    @Override
    public final IBlockState getStateFromMeta(int meta)
    {
        return mapper.getStateFromMeta(this, meta);
    }

    @Override
    public final int getMetaFromState(IBlockState state)
    {
        return mapper.getMetaFromState(state);
    }
}
