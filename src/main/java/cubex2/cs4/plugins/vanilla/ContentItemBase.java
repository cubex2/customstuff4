package cubex2.cs4.plugins.vanilla;

import com.google.common.collect.Maps;
import cubex2.cs4.CustomStuff4;
import cubex2.cs4.api.*;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.GameData;
import net.minecraftforge.registries.RegistryManager;

import java.util.Map;

public abstract class ContentItemBase<T extends Item> implements Content
{
    String id;
    int maxDamage = 0;

    Attribute<Color> tint = null;
    public Attribute<Integer> burnTime = Attribute.constant(-1);
    public Attribute<ResourceLocation> gui = Attribute.constant(null);
    public Map<String, ItemModuleSupplier> modules = Maps.newLinkedHashMap();

    protected transient T item;

    @Override
    public final void init(InitPhase phase, ContentHelper helper)
    {
        if (phase == InitPhase.PRE_INIT)
        {
            item = createItem();
            item.setUnlocalizedName(helper.getModId() + "." + id);
            item.setRegistryName(id);

            item.setMaxDamage(maxDamage);
            initItem();
        } else if (phase == InitPhase.REGISTER_ITEMS)
        {
            ForgeRegistry<Item> registry = RegistryManager.ACTIVE.getRegistry(GameData.ITEMS);
            registry.register(item);
        } else if (phase == InitPhase.REGISTER_MODELS)
        {
            registerModels();
        } else if (phase == InitPhase.INIT)
        {
            if (tint != null)
            {
                if (item.getHasSubtypes())
                    CustomStuff4.proxy.setItemTint(item, meta -> tint.get(meta).orElse(new ColorImpl(0xffffffff)).getRGB());
                else
                    CustomStuff4.proxy.setItemTint(item, meta -> tint.get(0).orElse(new ColorImpl(0xffffffff)).getRGB());
            }
        }
    }

    protected void registerModels()
    {

    }

    protected abstract void initItem();

    protected abstract T createItem();
}
