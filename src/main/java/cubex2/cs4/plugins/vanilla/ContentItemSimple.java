package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.CustomStuff4;
import cubex2.cs4.api.Content;
import cubex2.cs4.api.ContentHelper;
import cubex2.cs4.api.InitPhase;
import cubex2.cs4.plugins.vanilla.item.ItemSimple;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.Arrays;

public class ContentItemSimple implements Content
{
    public int[] subtypes = new int[0];
    public MetadataAttribute<String> creativeTab = MetadataAttribute.constant("anonexistingtabtoreturnnull");

    String id;
    MetadataAttribute<ResourceLocation> model = MetadataAttribute.constant(new ResourceLocation("minecraft:stick"));

    @Override
    public void init(InitPhase phase, ContentHelper helper)
    {
        if (phase != InitPhase.INIT)
            return;

        ItemSimple item = new ItemSimple(this);
        item.setUnlocalizedName(id);
        item.setRegistryName(id);
        item.setHasSubtypes(subtypes.length > 0);

        GameRegistry.register(item);

        if (subtypes.length == 0)
            subtypes = new int[] {0};

        Arrays.stream(subtypes).forEach(meta -> CustomStuff4.proxy.registerItemModel(item, meta, model.get(meta)));
    }
}
