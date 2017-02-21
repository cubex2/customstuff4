package cubex2.cs4.plugins.vanilla.item;

import cubex2.cs4.plugins.vanilla.ContentItemShovel;
import net.minecraft.item.ItemSpade;

public class ItemShovel extends ItemSpade implements ItemTool
{
    public ItemShovel(ToolMaterial material, ContentItemShovel content)
    {
        super(material);
    }

    @Override
    public void setDamage(float damage)
    {
        damageVsEntity = damage;
    }

    @Override
    public void setAttackSpeed(float attackSpeed)
    {
        this.attackSpeed = attackSpeed;
    }
}
