package cubex2.cs4.util;

import com.google.common.collect.Lists;
import cubex2.cs4.api.RecipeInput;
import cubex2.cs4.plugins.vanilla.Attribute;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.oredict.OreDictionary;

import java.util.*;

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

    public static List<ItemStack> createSubItems(Item item, CreativeTabs creativeTab, Attribute<String> tabLabels, int[] subtypes)
    {
        List<ItemStack> list = Lists.newArrayList();

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
        } else if (target instanceof List)
        {
            if (input instanceof ItemStack)
            {
                if (((List<ItemStack>) target).stream().noneMatch(targetStack -> OreDictionary.itemMatches(targetStack, (ItemStack) input, true)))
                    return false;
            } else if (!(input instanceof List) || input != target)
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

    public static boolean stackMatchesStackOrOreClass(ItemStack target, Object input)
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

    public static boolean stackMatchesRecipeInput(ItemStack stack, RecipeInput input, boolean checkCount)
    {
        if (input.isItemStack())
        {
            ItemStack inputStack = input.getStack().getItemStack();
            if (OreDictionary.itemMatches(inputStack, stack, false)
                && (!checkCount || inputStack.stackSize <= stack.stackSize))
                return true;
        } else
        {
            if (OreDictionary.containsMatch(false, OreDictionary.getOres(input.getOreClass().getOreName()), stack))
                return true;
        }

        return false;
    }

    public static void removeInputsFromInventory(List<RecipeInput> inputs, IItemHandlerModifiable inv, int start, int numSlots)
    {
        List<RecipeInput> remaining = Lists.newLinkedList(inputs);

        for (int i = start; i < start + numSlots; i++)
        {
            ItemStack stack = inv.getStackInSlot(i);
            for (Iterator<RecipeInput> iterator = remaining.iterator(); iterator.hasNext(); )
            {
                RecipeInput input = iterator.next();
                if (stackMatchesRecipeInput(stack, input, true))
                {
                    extractInput(input, inv, i);

                    iterator.remove();
                    break;
                }
            }
        }
    }

    static void extractInput(RecipeInput input, IItemHandlerModifiable from, int slot)
    {
        if (input.isOreClass())
        {
            from.extractItem(slot, input.getOreClass().getAmount(), false);
        } else
        {
            ItemStack toExtract = input.getStack().getItemStack();
            from.extractItem(slot, toExtract.stackSize, false);
        }
    }

    public static boolean fluidStackEqual(FluidStack stack, FluidStack input, boolean checkCount)
    {
        if (stack == null && input == null)
            return true;
        if (stack == null ^ input == null)
            return false;

        return stack.isFluidEqual(input) && (!checkCount || input.amount <= stack.amount);
    }

    public static boolean fluidStackEqual(FluidStack stack1, FluidStack stack2)
    {
        if (stack1 == null && stack2 == null)
            return true;
        if (stack1 == null ^ stack2 == null)
            return false;

        return stack1.isFluidEqual(stack2) && stack2.amount == stack1.amount;
    }

    public static void extractFluidsFromTanks(List<IFluidTank> tanks, List<FluidStack> fluids)
    {
        LinkedList<IFluidTank> remaining = Lists.newLinkedList(tanks);

        for (FluidStack stack : fluids)
        {
            for (Iterator<IFluidTank> iterator = remaining.iterator(); iterator.hasNext(); )
            {
                IFluidTank tank = iterator.next();
                if (tank.getFluid() != null && tank.getFluid().getFluid().getName().equals(stack.getFluid().getName()))
                {
                    FluidStack drained = tank.drain(stack.amount, false);
                    if (drained != null && drained.amount == stack.amount)
                    {
                        tank.drain(stack.amount, true);
                        iterator.remove();
                        break;
                    }
                }
            }
        }
    }
}
