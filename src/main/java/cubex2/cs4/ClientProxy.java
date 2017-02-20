package cubex2.cs4;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class ClientProxy extends CommonProxy
{
    @Override
    public void registerItemModel(Item item, int meta, ResourceLocation modelLocation)
    {
        ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();

        ModelBakery.registerItemVariants(item, modelLocation);

        ModelResourceLocation l = new ModelResourceLocation(modelLocation, "inventory");
        mesher.register(item, meta, l);
    }
}
