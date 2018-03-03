package cubex2.cs4.plugins.vanilla.block;

import cubex2.cs4.plugins.vanilla.ContentBlockPane;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

public class BlockPane extends net.minecraft.block.BlockPane implements CSBlock<ContentBlockPane>
{
    private final ContentBlockPane content;

    public BlockPane(Material materialIn, ContentBlockPane content)
    {
        super(materialIn, true);
        this.content = content;
    }

    @Override
    public int getSubtype(IBlockState state)
    {
        return 0;
    }

    @Override
    public ContentBlockPane getContent()
    {
        return content;
    }
}
