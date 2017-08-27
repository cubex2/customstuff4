package cubex2.cs4.plugins.vanilla;

import com.google.common.collect.Maps;
import cubex2.cs4.api.Content;
import cubex2.cs4.api.ContentHelper;
import cubex2.cs4.api.InitPhase;
import cubex2.cs4.api.TileEntityModuleSupplier;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import java.util.Map;

public abstract class ContentTileEntityBase implements Content
{
    public String id;
    public Map<String, TileEntityModuleSupplier> modules = Maps.newHashMap();

    private transient ResourceLocation key;

    @Override
    public void init(InitPhase phase, ContentHelper helper)
    {
        if (phase != InitPhase.INIT)
            return;

        String modId = helper.getModId();
        key = new ResourceLocation(modId, id);

        TileEntityRegistry.register(this);
    }

    public ResourceLocation getKey()
    {
        return key;
    }

    protected abstract Class<? extends TileEntity> getTemplateClass();
}
