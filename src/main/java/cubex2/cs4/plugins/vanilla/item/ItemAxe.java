package cubex2.cs4.plugins.vanilla.item;

import cubex2.cs4.plugins.vanilla.ContentItemAxe;

public class ItemAxe extends net.minecraft.item.ItemAxe implements ItemTool
{
    public ItemAxe(ToolMaterial material, ContentItemAxe content)
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
