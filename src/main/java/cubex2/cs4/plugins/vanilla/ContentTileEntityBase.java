package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.api.Content;
import cubex2.cs4.api.ContentHelper;
import cubex2.cs4.api.InitPhase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;

public abstract class ContentTileEntityBase implements Content
{
    public String id;

    private transient ResourceLocation key;

    @Override
    public void init(InitPhase phase, ContentHelper helper)
    {
        if (phase != InitPhase.INIT)
            return;

        String modId = Loader.instance().activeModContainer().getModId();
        key = new ResourceLocation(modId, id);

        TileEntityRegistry.register(this);
    }

    public ResourceLocation getKey()
    {
        return key;
    }

    protected abstract Class<? extends TileEntity> getTemplateClass();
}
