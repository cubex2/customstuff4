package cubex2.cs4.plugins.vanilla.crafting;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;

public class CraftingManagerCS4
{
    private static final Map<ResourceLocation, CraftingManagerCS4> instances = Maps.newHashMap();
    private final List<IRecipe> recipes = Lists.newArrayList();

    public static CraftingManagerCS4 getInstance(ResourceLocation list)
    {
        if (!instances.containsKey(list))
        {
            instances.put(list, new CraftingManagerCS4());
        }

        return instances.get(list);
    }

    public static List<IRecipe> getRecipes(ResourceLocation list)
    {
        if (list.toString().equals("minecraft:vanilla"))
        {
            return Lists.newArrayList(CraftingManager.REGISTRY.iterator());
        } else
        {
            return getInstance(list).recipes;
        }
    }

    public static void addRecipe(ResourceLocation list, IRecipe recipe)
    {
        checkArgument(!list.toString().equals("minecraft:vanilla"), "Trying to add a recipe for the vanilla list.");

        getRecipes(list).add(recipe);
    }

    public static ItemStack findMatchingRecipe(ResourceLocation list, InventoryCrafting craftMatrix, World worldIn)
    {
        return findMatchingRecipe(getRecipes(list), craftMatrix, worldIn);
    }

    public static ItemStack findMatchingRecipe(List<IRecipe> recipes, InventoryCrafting craftMatrix, World worldIn)
    {
        for (IRecipe irecipe : recipes)
        {
            if (irecipe.matches(craftMatrix, worldIn))
            {
                return irecipe.getCraftingResult(craftMatrix);
            }
        }

        return ItemStack.EMPTY;
    }
}
