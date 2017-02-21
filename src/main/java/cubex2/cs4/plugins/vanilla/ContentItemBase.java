package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.api.Content;
import cubex2.cs4.api.ContentHelper;
import cubex2.cs4.api.InitPhase;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public abstract class ContentItemBase<T extends Item> implements Content
{
    String id;

    protected transient T item;

    @Override
    public final void init(InitPhase phase, ContentHelper helper)
    {
        if (phase != InitPhase.INIT)
            return;

        item = createItem();
        item.setUnlocalizedName(id);
        item.setRegistryName(id);

        initItem();

        GameRegistry.register(item);
    }

    protected abstract void initItem();

    protected abstract T createItem();
}
