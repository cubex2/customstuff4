package cubex2.cs4.plugins.vanilla;

import com.google.common.collect.Maps;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.Map;

public class DamageableShapelessOreRecipe extends ShapelessOreRecipe
{
    private final int[] damageAmounts;
    private final int[] invSlots;

    public DamageableShapelessOreRecipe(ResourceLocation group, int[] damageAmounts, @Nonnull ItemStack result, Object... recipe)
    {
        super(group, result, recipe);
        this.damageAmounts = damageAmounts;
        invSlots = new int[damageAmounts.length];
    }

    @Nonnull
    @Override
    public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv)
    {
        NonNullList<ItemStack> items = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);

        matches(inv, null);

        for (int i = 0; i < invSlots.length; i++)
        {
            int amount = damageAmounts[i];
            int invIndex = invSlots[i];
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

        return items;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean matches(InventoryCrafting var1, @Nullable World world)
    {
        Map<Integer, Ingredient> required = Maps.newLinkedHashMap();
        for (int i = 0; i < input.size(); i++)
        {
            required.put(i, input.get(i));
        }

        for (int x = 0; x < var1.getSizeInventory(); x++)
        {
            ItemStack slot = var1.getStackInSlot(x);

            if (!slot.isEmpty())
            {
                boolean inRecipe = false;
                Iterator<Map.Entry<Integer, Ingredient>> req = required.entrySet().iterator();

                while (req.hasNext())
                {
                    boolean match = false;

                    Map.Entry<Integer, Ingredient> nextEntry = req.next();
                    Ingredient next = nextEntry.getValue();
                    int index = nextEntry.getKey();
                    int damage = damageAmounts[index];

                    match = next.apply(slot) && damage <= slot.getMaxDamage() - slot.getItemDamage() + 1;

                    if (match)
                    {
                        invSlots[index] = x;
                        inRecipe = true;
                        required.remove(index);
                        break;
                    }
                }

                if (!inRecipe)
                {
                    return false;
                }
            }
        }

        return required.isEmpty();
    }
}
