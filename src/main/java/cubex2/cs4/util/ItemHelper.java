package cubex2.cs4.util;

import com.google.common.collect.Lists;
import cubex2.cs4.api.OreClass;
import cubex2.cs4.api.RecipeInput;
import cubex2.cs4.api.WrappedItemStack;
import cubex2.cs4.plugins.vanilla.Attribute;
import cubex2.cs4.plugins.vanilla.BlockDrop;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.oredict.OreDictionary;

import java.lang.reflect.Field;
import java.util.*;

public class ItemHelper
{
    private static Field tabLabelField;

    public static Optional<CreativeTabs> findCreativeTab(String tabLabel)
    {
        return Arrays.stream(CreativeTabs.CREATIVE_TAB_ARRAY)
                     .filter(tab -> tab != null && Objects.equals(getTabLabel(tab), tabLabel))
                     .findFirst();
    }

    private static String getTabLabel(CreativeTabs tab)
    {
        if (tabLabelField == null)
        {
            tabLabelField = ReflectionHelper.findField(CreativeTabs.class, "tabLabel", "field_78034_o", "o");
            tabLabelField.setAccessible(true);
        }

        try
        {
            return (String) tabLabelField.get(tab);
        } catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }

        return null;
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
                                        if (creativeTab == null || creativeTab == CreativeTabs.SEARCH || Objects.equals(tabLabel, getTabLabel(creativeTab)))
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
    public static boolean isSameRecipeInput(Ingredient target, Object input)
    {
        if (input instanceof String)
        {
            NonNullList<ItemStack> ores = OreDictionary.getOres(input.toString());
            return ores.stream().allMatch(target::apply);
        } else if (input instanceof ItemStack)
        {
            return target.apply((ItemStack) input);
        } else if (input instanceof NonNullList)
        {
            NonNullList<ItemStack> items = (NonNullList<ItemStack>) input;
            return items.stream().anyMatch(target::apply);
        } else
        {
            throw new IllegalArgumentException("Invalid input: " + input);
        }
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
                && (!checkCount || inputStack.getCount() <= stack.getCount()))
                return true;
        } else
        {
            OreClass oreClass = input.getOreClass();
            if (OreDictionary.containsMatch(false, OreDictionary.getOres(oreClass.getOreName()), stack)
                && (!checkCount || oreClass.getAmount() <= stack.getCount()))
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
            from.extractItem(slot, toExtract.getCount(), false);
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

    public static ItemStack copyStack(ItemStack stack, int newAmount)
    {
        ItemStack copy = stack.copy();
        copy.setCount(newAmount);
        return copy;
    }

    public static List<ItemStack> getDroppedStacks(BlockDrop[] drops, int fortune)
    {
        List<ItemStack> result = Lists.newArrayList();

        for (BlockDrop drop : drops)
        {
            WrappedItemStack wrappedItemStack = drop.getItem();
            ItemStack droppedStack = wrappedItemStack.getItemStack();

            if (!droppedStack.isEmpty())
            {
                int amount = drop.getAmount(fortune);
                if (amount > 0)
                {
                    result.add(ItemHelper.copyStack(droppedStack, amount));
                }
            }
        }

        return result;
    }
}
