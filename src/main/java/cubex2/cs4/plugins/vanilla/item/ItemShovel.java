package cubex2.cs4.plugins.vanilla.item;

import cubex2.cs4.plugins.vanilla.ContentItemShovel;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

public class ItemShovel extends ItemSpade implements ItemTool
{
    private final ContentItemShovel content;

    public ItemShovel(ToolMaterial material, ContentItemShovel content)
    {
        super(material);
        this.content = content;
    }

    @Override
    public void setDamage(float damage)
    {
        attackDamage = damage;
    }

    @Override
    public void setAttackSpeed(float attackSpeed)
    {
        this.attackSpeed = attackSpeed;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        tooltip.addAll(Arrays.asList(content.information));
    }

    @Override
    public int getItemBurnTime(ItemStack itemStack)
    {
        return content.burnTime.get(itemStack.getMetadata()).orElse(-1);
    }
}
