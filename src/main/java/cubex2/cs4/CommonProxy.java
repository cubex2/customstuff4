package cubex2.cs4;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class CommonProxy
{
    public void registerItemModel(Item item, int meta, ResourceLocation modelLocation)
    {
        // NO OP
    }

    public EntityPlayer getClientPlayer()
    {
        return null;
    }
}
