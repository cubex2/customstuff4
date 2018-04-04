package cubex2.cs4.plugins.vanilla.block;

import cubex2.cs4.plugins.vanilla.ContentBlockTrapDoor;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockTrapDoor extends net.minecraft.block.BlockTrapDoor implements CSBlock<ContentBlockTrapDoor>
{
    private final ContentBlockTrapDoor content;

    public BlockTrapDoor(Material materialIn, ContentBlockTrapDoor content)
    {
        super(materialIn);
        this.content = content;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (!content.opensWithHands)
            return true;

        state = state.cycleProperty(OPEN);
        worldIn.setBlockState(pos, state, 2);
        this.playSound(playerIn, worldIn, pos, state.getValue(OPEN).booleanValue());
        return true;
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        if (content.opensWithRedstone)
        {
            super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
        }
    }

    @Override
    public int getSubtype(IBlockState state)
    {
        return 0;
    }

    @Override
    public ContentBlockTrapDoor getContent()
    {
        return content;
    }
}
