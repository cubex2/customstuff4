package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.CustomStuff4;
import cubex2.cs4.api.Color;
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

    Attribute<Color> tint = null;

    protected transient T item;

    @Override
    public final void init(InitPhase phase, ContentHelper helper)
    {
        if (phase == InitPhase.INIT && item != null)
        {
            if (tint != null)
            {
                if (item.getHasSubtypes())
                    CustomStuff4.proxy.setItemTint(item, meta -> tint.get(meta).orElse(new ColorImpl(0xffffffff)).getRGB());
                else
                    CustomStuff4.proxy.setItemTint(item, meta -> tint.get(0).orElse(new ColorImpl(0xffffffff)).getRGB());
            }
        }

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
