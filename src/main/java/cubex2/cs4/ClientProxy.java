package cubex2.cs4;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;

public class ClientProxy extends CommonProxy
{
    @Override
    public void registerItemModel(Item item, int meta, ResourceLocation modelLocation)
    {
        ModelResourceLocation l = new ModelResourceLocation(modelLocation, "inventory");
        ModelLoader.setCustomModelResourceLocation(item, meta, l);
    }
}
