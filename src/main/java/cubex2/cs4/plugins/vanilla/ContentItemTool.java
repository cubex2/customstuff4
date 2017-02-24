package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.api.WrappedToolMaterial;
import cubex2.cs4.plugins.vanilla.item.ItemTool;
import net.minecraft.item.Item;

public abstract class ContentItemTool<T extends Item & ItemTool> extends ContentItemNoSubtypes<T>
{
    Float damage = null;
    Float attackSpeed = null;
    Integer durability = null;

    WrappedToolMaterial material = WrappedToolMaterial.of(Item.ToolMaterial.WOOD);

    public ContentItemTool()
    {
        creativeTab = "tools";
    }

    @Override
    protected void initItem()
    {
        if (damage != null)
            item.setDamage(damage);
        if (attackSpeed != null)
            item.setAttackSpeed(attackSpeed);
        if (durability != null)
            item.setMaxDamage(durability);

        super.initItem();
    }
}
