package cubex2.cs4.plugins.vanilla.item;

import cubex2.cs4.api.WrappedItemStack;
import cubex2.cs4.api.WrappedPotionEffect;
import cubex2.cs4.plugins.vanilla.ContentItemFood;
import cubex2.cs4.plugins.vanilla.ContentItemWithSubtypes;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import java.util.Optional;

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
    public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
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
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving)
    {
        stack = super.onItemUseFinish(stack, worldIn, entityLiving);

        Optional<WrappedItemStack> result = content.result.get(stack.getMetadata());
        if (result.isPresent())
        {
            if (stack == null)
            {
                return result.get().getItemStack().copy();
            }

            EntityPlayer player = entityLiving instanceof EntityPlayer ? (EntityPlayer) entityLiving : null;

            if (player != null)
            {
                player.inventory.addItemStackToInventory(result.get().getItemStack().copy());
            }
        }

        return stack;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack)
    {
        return content.useAction.get(stack.getMetadata()).orElse(EnumAction.EAT);
    }

    @Override
    public ContentItemWithSubtypes<?> getContent()
    {
        return content;
    }
}
