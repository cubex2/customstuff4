package cubex2.cs4.util;

import cubex2.cs4.plugins.vanilla.Attribute;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public class ItemHelper
{
    public static Optional<CreativeTabs> findCreativeTab(String tabLabel)
    {
        return Arrays.stream(CreativeTabs.CREATIVE_TAB_ARRAY)
                     .filter(tab -> tab != null && tab.getTabLabel().equals(tabLabel))
                     .findFirst();
    }

    public static CreativeTabs[] createCreativeTabs(Attribute<String> tabLabels, int[] subtypes)
    {
        return Arrays.stream(subtypes).mapToObj(tabLabels::get)
                     .filter(Optional::isPresent)
                     .map(Optional::get)
                     .map(ItemHelper::findCreativeTab)
                     .filter(Optional::isPresent)
                     .map(Optional::get)
                     .distinct()
                     .toArray(CreativeTabs[]::new);
    }

    public static NonNullList<ItemStack> createSubItems(Item item, CreativeTabs creativeTab, Attribute<String> tabLabels, int[] subtypes)
    {
        NonNullList<ItemStack> list = NonNullList.create();

        if (item.getHasSubtypes())
        {
            for (int meta : subtypes)
            {
                tabLabels.get(meta)
                         .ifPresent(tabLabel ->
                                    {
                                        if (creativeTab == null || Objects.equals(tabLabel, creativeTab.getTabLabel()))
                                        {
                                            list.add(new ItemStack(item, 1, meta));
                                        }
                                    });
            }
        } else
        {
            list.add(new ItemStack(item, 1, 0));
        }

        return list;
    }

    @SuppressWarnings("unchecked")
    public static boolean isSameRecipeInput(Object target, Object input)
    {
        if (target instanceof String)
        {
            if (!(input instanceof String) || !target.equals(input))
                return false;
        } else if (target instanceof ItemStack)
        {
            if (!(input instanceof ItemStack) || !OreDictionary.itemMatches((ItemStack) target, (ItemStack) input, true))
                return false;
        } else if (target instanceof NonNullList)
        {
            if (input instanceof ItemStack)
            {
                if (((NonNullList<ItemStack>) target).stream().noneMatch(targetStack -> OreDictionary.itemMatches(targetStack, (ItemStack) input, true)))
                    return false;
            } else if (!(input instanceof NonNullList) || input != target)
                return false;
        }

        return true;
    }

    public static boolean isSameStackForFuel(ItemStack fuel, ItemStack stack)
    {
        boolean itemEqual = stack.getMetadata() == OreDictionary.WILDCARD_VALUE
                            ? fuel.isItemEqualIgnoreDurability(stack)
                            : fuel.isItemEqual(stack);

        boolean nbtEqual = !stack.hasTagCompound() || ItemStack.areItemStackTagsEqual(stack, fuel);

        return itemEqual && nbtEqual;
    }

    public static boolean isSameStackForMachineInput(ItemStack target, Object input)
    {
        if (input instanceof ItemStack)
        {
            return isSameStackForFuel(target, (ItemStack) input);
        }

        if (input instanceof String)
        {
            return OreDictionary.containsMatch(false, OreDictionary.getOres((String) input), target);
        }

        return false;
    }
}
