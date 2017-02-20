package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.CustomStuff4;
import cubex2.cs4.api.Content;
import cubex2.cs4.api.ContentHelper;
import cubex2.cs4.api.InitPhase;
import cubex2.cs4.plugins.vanilla.item.ItemSimple;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ContentItemSimple implements Content
{
    String id;
    ResourceLocation model;

    @Override
    public void init(InitPhase phase, ContentHelper helper)
    {
        if (phase != InitPhase.INIT)
            return;

        ItemSimple item = new ItemSimple();
        item.setUnlocalizedName(id);
        item.setRegistryName(id);
        item.setCreativeTab(CreativeTabs.TOOLS);

        GameRegistry.register(item);
        CustomStuff4.proxy.registerItemModel(item, 0, model);
    }
}
