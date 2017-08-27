package cubex2.cs4.plugins.vanilla;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.oredict.ShapedOreRecipe;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.Set;

public class DamageableShapedOreRecipe extends ShapedOreRecipe
{
    private final int[] damageAmounts;
    private final int[] mirroredDamageAmounts;
    private int matchX;
    private int matchY;
    private boolean wasMirrored;

    public DamageableShapedOreRecipe(@Nullable ResourceLocation group, int[] damageAmounts, @Nonnull ItemStack result, Object... recipe)
    {
        super(group, result, recipe);
        this.damageAmounts = damageAmounts;
        mirroredDamageAmounts = mirror(damageAmounts);
    }

    private DamageableShapedOreRecipe(@Nullable ResourceLocation group, int[] damageAmounts, @Nonnull ItemStack result, CraftingHelper.ShapedPrimer primer)
    {
        super(group, result, primer);
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
                Ingredient target = Ingredient.EMPTY;

                if (subX >= 0 && subY >= 0 && subX < width && subY < height)
                {
                    damage = amounts[subX + width * subY];

                    if (mirror)
                    {
                        target = input.get(width - subX - 1 + subY * width);
                    } else
                    {
                        target = input.get(subX + subY * width);
                    }
                }

                ItemStack slot = inv.getStackInRowAndColumn(x, y);

                if (!target.apply(slot) || damage > slot.getMaxDamage() - slot.getItemDamage() + 1)
                {
                    return false;
                }
            }
        }

        return true;
    }

    @SuppressWarnings("unused")
    public static class Factory implements IRecipeFactory
    {
        @Override
        public IRecipe parse(JsonContext context, JsonObject json)
        {
            String group = JsonUtils.getString(json, "group", "");
            //if (!group.isEmpty() && group.indexOf(':') == -1)
            //    group = context.getModId() + ":" + group;

            Map<Character, Ingredient> ingMap = Maps.newHashMap();
            for (Map.Entry<String, JsonElement> entry : JsonUtils.getJsonObject(json, "key").entrySet())
            {
                if (entry.getKey().length() != 1)
                    throw new JsonSyntaxException("Invalid key entry: '" + entry.getKey() + "' is an invalid symbol (must be 1 character only).");
                if (" ".equals(entry.getKey()))
                    throw new JsonSyntaxException("Invalid key entry: ' ' is a reserved symbol.");

                ingMap.put(entry.getKey().toCharArray()[0], CraftingHelper.getIngredient(entry.getValue(), context));
            }

            ingMap.put(' ', Ingredient.EMPTY);

            JsonArray patternJ = JsonUtils.getJsonArray(json, "pattern");

            if (patternJ.size() == 0)
                throw new JsonSyntaxException("Invalid pattern: empty pattern not allowed");

            String[] pattern = new String[patternJ.size()];
            for (int x = 0; x < pattern.length; ++x)
            {
                String line = JsonUtils.getString(patternJ.get(x), "pattern[" + x + "]");
                if (x > 0 && pattern[0].length() != line.length())
                    throw new JsonSyntaxException("Invalid pattern: each row must  be the same width");
                pattern[x] = line;
            }

            CraftingHelper.ShapedPrimer primer = new CraftingHelper.ShapedPrimer();
            primer.width = pattern[0].length();
            primer.height = pattern.length;
            primer.mirrored = JsonUtils.getBoolean(json, "mirrored", true);
            primer.input = NonNullList.withSize(primer.width * primer.height, Ingredient.EMPTY);

            Set<Character> keys = Sets.newHashSet(ingMap.keySet());
            keys.remove(' ');

            int x = 0;
            for (String line : pattern)
            {
                for (char chr : line.toCharArray())
                {
                    Ingredient ing = ingMap.get(chr);
                    if (ing == null)
                        throw new JsonSyntaxException("Pattern references symbol '" + chr + "' but it's not defined in the key");
                    primer.input.set(x++, ing);
                    keys.remove(chr);
                }
            }

            if (!keys.isEmpty())
                throw new JsonSyntaxException("Key defines symbols that aren't used in pattern: " + keys);

            ItemStack result = CraftingHelper.getItemStack(JsonUtils.getJsonObject(json, "result"), context);

            Map<Character, Integer> damageMap = Maps.newHashMap();
            for (Map.Entry<String, JsonElement> entry : JsonUtils.getJsonObject(json, "damage").entrySet())
            {
                if (entry.getKey().length() != 1)
                    throw new JsonSyntaxException("Invalid key entry: '" + entry.getKey() + "' is an invalid symbol (must be 1 character only).");
                if (" ".equals(entry.getKey()))
                    throw new JsonSyntaxException("Invalid key entry: ' ' is a reserved symbol.");
                if (!entry.getValue().isJsonPrimitive() || !entry.getValue().getAsJsonPrimitive().isNumber())
                    throw new JsonSyntaxException("Invalid key value: '" + entry.getValue() + "' is not a number.");

                damageMap.put(entry.getKey().toCharArray()[0], entry.getValue().getAsInt());
            }

            int[] damageAmounts = ShapedRecipe.createDamageAmounts(primer.width, primer.height, pattern, damageMap);
            return new DamageableShapedOreRecipe(group.isEmpty() ? null : new ResourceLocation(group), damageAmounts, result, primer);
        }
    }
}
