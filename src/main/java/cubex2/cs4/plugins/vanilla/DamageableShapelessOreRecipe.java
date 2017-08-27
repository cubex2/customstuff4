package cubex2.cs4.plugins.vanilla;

import com.google.common.collect.Maps;
import com.google.gson.*;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.Map;

public class DamageableShapelessOreRecipe extends ShapelessOreRecipe
{
    private final int[] damageAmounts;
    private final int[] invSlots;

    public DamageableShapelessOreRecipe(@Nullable ResourceLocation group, int[] damageAmounts, @Nonnull ItemStack result, Object... recipe)
    {
        super(group, result, recipe);
        this.damageAmounts = damageAmounts;
        invSlots = new int[damageAmounts.length];
    }

    private DamageableShapelessOreRecipe(@Nullable ResourceLocation group, int[] damageAmounts, NonNullList<Ingredient> input, @Nonnull ItemStack result)
    {
        super(group, input, result);
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

    @SuppressWarnings("unused")
    public static class Factory implements IRecipeFactory
    {
        @Override
        public IRecipe parse(JsonContext context, JsonObject json)
        {
            String group = JsonUtils.getString(json, "group", "");

            NonNullList<Ingredient> ings = NonNullList.create();
            for (JsonElement ele : JsonUtils.getJsonArray(json, "ingredients"))
                ings.add(CraftingHelper.getIngredient(ele, context));

            if (ings.isEmpty())
                throw new JsonParseException("No ingredients for shapeless recipe");

            ItemStack itemstack = CraftingHelper.getItemStack(JsonUtils.getJsonObject(json, "result"), context);

            int[] damage = new int[ings.size()];
            if (JsonUtils.hasField(json, "damage"))
            {
                JsonArray array = JsonUtils.getJsonArray(json, "damage");
                if (array.size() > damage.length)
                    throw new JsonParseException("Too many values for damage array: got " + array.size() + ", expected " + damage.length);

                for (int i = 0; i < array.size(); i++)
                {
                    JsonElement element = array.get(i);
                    if (!element.isJsonPrimitive() || !element.getAsJsonPrimitive().isNumber())
                        throw new JsonSyntaxException("Entry in damage array is not a number, got " + element);

                    damage[i] = element.getAsJsonPrimitive().getAsInt();
                }
            }
            return new DamageableShapelessOreRecipe(group.isEmpty() ? null : new ResourceLocation(group), damage, ings, itemstack);
        }
    }
}
