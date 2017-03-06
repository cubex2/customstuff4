package cubex2.cs4.plugins.vanilla.item;

import cubex2.cs4.api.WrappedPotionEffect;
import cubex2.cs4.plugins.vanilla.ContentItemFood;
import cubex2.cs4.util.ItemHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.List;

public class ItemFood extends net.minecraft.item.ItemFood
{
    private final ContentItemFood content;
    private CreativeTabs[] tabs;

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
    public String getUnlocalizedName(ItemStack stack)
    {
        if (hasSubtypes)
        {
            return super.getUnlocalizedName(stack) + "." + stack.getMetadata();
        } else
        {
            return super.getUnlocalizedName(stack);
        }
    }

    @Override
    public CreativeTabs[] getCreativeTabs()
    {
        if (tabs == null)
        {
            tabs = ItemHelper.createCreativeTabs(content.creativeTab, content.subtypes);
        }

        return tabs;
    }

    @Override
    public int getItemStackLimit(ItemStack stack)
    {
        return content.maxStack.get(stack.getMetadata()).orElse(64);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced)
    {
        String[] lines = content.information.get(stack.getMetadata()).orElse(new String[0]);
        tooltip.addAll(Arrays.asList(lines));
    }

    @Override
    public void getSubItems(Item itemIn, CreativeTabs creativeTab, NonNullList<ItemStack> subItems)
    {
        subItems.addAll(ItemHelper.createSubItems(itemIn, creativeTab, content.creativeTab, content.subtypes));
    }
}
