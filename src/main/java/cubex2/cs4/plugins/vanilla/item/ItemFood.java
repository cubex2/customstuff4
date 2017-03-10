package cubex2.cs4.plugins.vanilla.item;

import cubex2.cs4.api.WrappedPotionEffect;
import cubex2.cs4.plugins.vanilla.ContentItemFood;
import cubex2.cs4.plugins.vanilla.ContentItemWithSubtypes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemFood extends net.minecraft.item.ItemFood implements ItemWithSubtypes
{
    private final ContentItemFood content;

    public ItemFood(ContentItemFood content)
    {
        super(content.healAmount.get(0).orElse(2),
              content.saturation.get(0).orElse(0.6F),
              content.isWolfFood);

        this.content = content;
    }

    @Override
    public int getHealAmount(ItemStack stack)
    {
        return content.healAmount.get(stack.getMetadata()).orElse(2);
    }

    @Override
    public float getSaturationModifier(ItemStack stack)
    {
        return content.saturation.get(stack.getMetadata()).orElse(0.6F);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack stack = playerIn.getHeldItem(handIn);

        if (playerIn.canEat(content.alwaysEdible.get(stack.getMetadata()).orElse(false)))
        {
            playerIn.setActiveHand(handIn);
            return new ActionResult<>(EnumActionResult.SUCCESS, stack);
        } else
        {
            return new ActionResult<>(EnumActionResult.FAIL, stack);
        }
    }

    @Override
    protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player)
    {
        WrappedPotionEffect effect = content.potionEffect.get(stack.getMetadata()).orElse(WrappedPotionEffect.of(null));
        float probability = content.potionEffectProbability.get(stack.getMetadata()).orElse(1f);
        PotionEffect potion = effect.getPotionEffect();

        if (!worldIn.isRemote && potion != null && worldIn.rand.nextFloat() < probability)
        {
            player.addPotionEffect(new PotionEffect(potion));
        }
    }

    @Override
    public ContentItemWithSubtypes<?> getContent()
    {
        return content;
    }
}
