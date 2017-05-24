package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.api.Content;
import cubex2.cs4.api.ContentHelper;
import cubex2.cs4.api.InitPhase;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.GameRegistry;

public abstract class ContentItemBase<T extends Item> implements Content
{
    String id;
    int maxDamage = 0;

    protected transient T item;

    @Override
    public final void init(InitPhase phase, ContentHelper helper)
    {
        if (phase != InitPhase.PRE_INIT)
            return;

        item = createItem();
        item.setUnlocalizedName(Loader.instance().activeModContainer().getModId() + "." + id);
        item.setRegistryName(id);

        item.setMaxDamage(maxDamage);
        initItem();

        GameRegistry.register(item);
    }

    protected abstract void initItem();

    protected abstract T createItem();
}
