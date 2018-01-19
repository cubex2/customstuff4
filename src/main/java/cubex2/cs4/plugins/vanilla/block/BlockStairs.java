package cubex2.cs4.plugins.vanilla.block;

import cubex2.cs4.plugins.vanilla.ContentBlockStairs;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

public class BlockStairs extends net.minecraft.block.BlockStairs implements CSBlock<ContentBlockStairs>
{
    private final ContentBlockStairs content;

    public BlockStairs(Material material, ContentBlockStairs content)
    {
        super(content.modelState.createState());
        this.content = content;
    }

    @Override
    public int getSubtype(IBlockState state)
    {
        return 0;
    }

    @Override
    public ContentBlockStairs getContent()
    {
        return content;
    }
}
