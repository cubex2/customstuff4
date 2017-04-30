package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.api.Content;
import cubex2.cs4.api.ContentHelper;
import cubex2.cs4.api.InitPhase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;

import javax.annotation.Nullable;

public abstract class ContentGuiBase implements Content
{
    public String id;

    private transient ResourceLocation key;
    private transient int guiId;

    @Override
    public void init(InitPhase phase, ContentHelper helper)
    {
        if (phase != InitPhase.INIT)
            return;

        String modId = Loader.instance().activeModContainer().getModId();
        key = new ResourceLocation(modId, id);

        guiId = GuiRegistry.register(this);
    }

    public ResourceLocation getKey()
    {
        return key;
    }

    public int getGuiId()
    {
        return guiId;
    }

    @Nullable
    protected abstract Object getServerGuiElement(EntityPlayer player, World world, int x, int y, int z);

    @Nullable
    protected abstract Object getClientGuiElement(EntityPlayer player, World world, int x, int y, int z);
}
