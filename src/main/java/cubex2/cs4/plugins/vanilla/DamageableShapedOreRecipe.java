package cubex2.cs4.plugins.vanilla;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

import javax.annotation.Nonnull;
import java.util.Iterator;
import java.util.List;

class DamageableShapedOreRecipe extends ShapedOreRecipe
{
    private final int[] damageAmounts;
    private final int[] mirroredDamageAmounts;
    private int matchX;
    private int matchY;
    private boolean wasMirrored;

    public DamageableShapedOreRecipe(int[] damageAmounts, @Nonnull ItemStack result, Object... recipe)
    {
        super(result, recipe);
        this.damageAmounts = damageAmounts;
        mirroredDamageAmounts = mirror(damageAmounts);
    }

    private int[] mirror(int[] array)
    {
        int[] result = new int[array.length];

        for (int col = 0; col < getWidth(); col++)
        {
            for (int row = 0; row < getHeight(); row++)
            {
                result[getWidth() - 1 - col + row * getWidth()] = array[col + row * getWidth()];
            }
        }

        return result;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv)
    {
        NonNullList<ItemStack> items = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);

        getMatch(inv);

        int[] amounts = getAmounts(wasMirrored);

        for (int col = 0; col < getWidth(); col++)
        {
            for (int row = 0; row < getHeight(); row++)
            {
                int amountIndex = col + row * getWidth();
                int invIndex = matchX + col + (row + matchY) * inv.getWidth();
                int amount = amounts[amountIndex];
                if (amount > 0)
                {
                    ItemStack stack = inv.getStackInSlot(invIndex).copy();
                    stack.setItemDamage(stack.getItemDamage() + amount);
                    if (stack.getItemDamage() > stack.getMaxDamage())
                    {
                        stack = ForgeHooks.getContainerItem(stack);
                    }
                    items.set(invIndex, stack);
                } else
                {
                    items.set(invIndex, ForgeHooks.getContainerItem(inv.getStackInSlot(invIndex)));
                }
            }
        }

        return items;
    }

    private int[] getAmounts(boolean mirror)
    {
        return mirror ? mirroredDamageAmounts
                      : damageAmounts;
    }

    private void getMatch(InventoryCrafting inv)
    {
        for (int x = 0; x <= MAX_CRAFT_GRID_WIDTH - width; x++)
        {
            for (int y = 0; y <= MAX_CRAFT_GRID_HEIGHT - height; ++y)
            {
                if (checkMatch(inv, x, y, false))
                {
                    matchX = x;
                    matchY = y;
                    wasMirrored = false;
                    return;
                }

                if (mirrored && checkMatch(inv, x, y, true))
                {
                    matchX = x;
                    matchY = y;
                    wasMirrored = true;
                    return;
                }
            }
        }

        matchX = matchY = -1;
    }

    @Override
    protected boolean checkMatch(InventoryCrafting inv, int startX, int startY, boolean mirror)
    {
        int[] amounts = getAmounts(mirror);

        for (int x = 0; x < MAX_CRAFT_GRID_WIDTH; x++)
        {
            for (int y = 0; y < MAX_CRAFT_GRID_HEIGHT; y++)
            {
                int subX = x - startX;
                int subY = y - startY;
                int damage = 0;
                Object target = null;

                if (subX >= 0 && subY >= 0 && subX < width && subY < height)
                {
                    damage = amounts[subX + width * subY];

                    if (mirror)
                    {
                        target = input[width - subX - 1 + subY * width];
                    } else
                    {
                        target = input[subX + subY * width];
                    }
                }

                ItemStack slot = inv.getStackInRowAndColumn(x, y);

                if (target instanceof ItemStack)
                {
                    ItemStack stack = (ItemStack) target;
                    if (!OreDictionary.itemMatches(stack, slot, false) || damage > stack.getMaxDamage() - stack.getItemDamage() + 1)
                    {
                        return false;
                    }
                } else if (target instanceof List)
                {
                    boolean matched = false;

                    Iterator<ItemStack> itr = ((List<ItemStack>) target).iterator();
                    while (itr.hasNext() && !matched)
                    {
                        ItemStack stack = itr.next();
                        matched = OreDictionary.itemMatches(stack, slot, false) && damage <= stack.getMaxDamage() - stack.getItemDamage() + 1;
                    }

                    if (!matched)
                    {
                        return false;
                    }
                } else if (target == null && !slot.isEmpty())
                {
                    return false;
                }
            }
        }

        return true;
    }
}
