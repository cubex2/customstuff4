package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.CustomStuff4;
import cubex2.cs4.api.WrappedToolMaterial;
import cubex2.cs4.plugins.vanilla.item.ItemTool;
import cubex2.cs4.util.ItemHelper;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public abstract class ContentItemTool<T extends Item & ItemTool> extends ContentItemBase<T>
{
    Float damage = null;
    Float attackSpeed = null;
    String creativeTab = "tools";

    WrappedToolMaterial material = WrappedToolMaterial.of(Item.ToolMaterial.WOOD);
    ResourceLocation model = new ResourceLocation("minecraft:stick");

    @Override
    protected void initItem()
    {
        item.setCreativeTab(ItemHelper.findCreativeTab(creativeTab).orElse(null));

        if (damage != null)
            item.setDamage(damage);
        if (attackSpeed != null)
            item.setAttackSpeed(attackSpeed);

        CustomStuff4.proxy.registerItemModel(item, 0, model);
    }
}
