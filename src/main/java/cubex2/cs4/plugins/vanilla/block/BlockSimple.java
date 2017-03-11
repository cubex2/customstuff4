package cubex2.cs4.plugins.vanilla.block;

import cubex2.cs4.plugins.vanilla.ContentBlockSimple;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

public abstract class BlockSimple extends Block implements CSBlock<ContentBlockSimple>
{
    private final ContentBlockSimple content;

    public BlockSimple(Material material, ContentBlockSimple content)
    {
        super(material);

        this.content = content;
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return getSubtype(state);
    }

    @Override
    public int getSubtype(IBlockState state)
    {
        return getMetaFromState(state);
    }

    @Override
    public ContentBlockSimple getContent()
    {
        return content;
    }
}
