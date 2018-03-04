package cubex2.cs4.plugins.vanilla.item;

import cubex2.cs4.CustomStuff4;
import cubex2.cs4.plugins.vanilla.ContentGuiBase;
import cubex2.cs4.plugins.vanilla.ContentItemWithSubtypes;
import cubex2.cs4.plugins.vanilla.GuiRegistry;
import cubex2.cs4.util.ItemHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.common.FMLLog;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public abstract class ItemWithSubtypesMixin extends Item implements ItemWithSubtypes
{
    private CreativeTabs[] tabs;

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
    public int getItemStackLimit(ItemStack stack)
    {
        return getContent().maxStack.get(stack.getMetadata()).orElse(64);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        String[] lines = getContent().information.get(stack.getMetadata()).orElse(new String[0]);
        tooltip.addAll(Arrays.asList(lines));
    }

    @Override
    public CreativeTabs[] getCreativeTabs()
    {
        if (tabs == null)
        {
            tabs = ItemHelper.createCreativeTabs(getContent().creativeTab, getContent().subtypes);
        }

        return tabs;
    }

    @Override
    public CreativeTabs getCreativeTab()
    {
        CreativeTabs[] tabs = getCreativeTabs();
        return tabs.length == 0 ? null : tabs[0];
    }

    @Override
    public void getSubItems(CreativeTabs creativeTab, NonNullList<ItemStack> subItems)
    {
        if (isInCreativeTab(creativeTab))
        {
            subItems.addAll(ItemHelper.createSubItems(this, creativeTab, getContent().creativeTab, getContent().subtypes));
        }
    }

    @Override
    public int getItemBurnTime(ItemStack itemStack)
    {
        return getContent().burnTime.get(itemStack.getMetadata()).orElse(-1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack stack = playerIn.getHeldItem(handIn);
        Optional<ContentGuiBase> gui = getGui(stack.getMetadata());
        if (gui.isPresent())
        {
            playerIn.openGui(CustomStuff4.INSTANCE, gui.get().getGuiId(), worldIn, handIn.ordinal(), -1, 0);
            return new ActionResult<>(EnumActionResult.SUCCESS, stack);
        } else
        {
            return new ActionResult<>(EnumActionResult.PASS, stack);
        }
    }

    private Optional<ContentGuiBase> getGui(int subtype)
    {
        Optional<ResourceLocation> location = getContent().gui.get(subtype);
        if (location.isPresent())
        {
            ContentGuiBase gui = GuiRegistry.get(location.get());
            if (gui == null)
            {
                FMLLog.warning("Missing GUI %s", location.get());
            }
            return Optional.ofNullable(gui);
        }
        return Optional.empty();
    }

     @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt)
    {
        if (!getContent().modules.isEmpty())
        {
            return new CapabilityProviderItem(stack, getContent());
        } else
        {
            return null;
        }
    }

    @Override
    public ContentItemWithSubtypes<?> getContent()
    {
        return null;
    }
}
