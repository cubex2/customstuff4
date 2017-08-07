package cubex2.cs4;

import cubex2.cs4.api.BlockTint;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import java.util.function.IntFunction;

public class CommonProxy
{
    public void registerItemModel(Item item, int meta, ResourceLocation modelLocation)
    {
        // NO OP
    }

    public void setBlockBiomeTint(Block block, IntFunction<BlockTint> tintTypeForSubtype)
    {
        // NO OP
    }

    public void setItemTint(Item item, IntFunction<Integer> itemTint)
    {
        // NO OP
    }

    public EntityPlayer getClientPlayer()
    {
        return null;
    }
}
