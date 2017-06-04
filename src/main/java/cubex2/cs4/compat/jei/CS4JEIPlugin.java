package cubex2.cs4.compat.jei;

import cubex2.cs4.api.WrappedItemStack;
import cubex2.cs4.plugins.jei.*;
import cubex2.cs4.plugins.vanilla.MachineRecipeImpl;
import cubex2.cs4.plugins.vanilla.crafting.CraftingManagerCS4;
import cubex2.cs4.plugins.vanilla.crafting.MachineManager;
import mezz.jei.api.BlankModPlugin;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import net.minecraft.item.ItemStack;

import java.util.List;
import java.util.stream.Collectors;

@JEIPlugin
public class CS4JEIPlugin extends BlankModPlugin
{
    @Override
    public void register(IModRegistry registry)
    {
        IJeiHelpers jeiHelpers = registry.getJeiHelpers();

        addMachineRecipes(registry, jeiHelpers);
        addCraftingRecipes(registry, jeiHelpers);
        addDescriptions(registry);
    }

    private void addDescriptions(IModRegistry registry)
    {
        for (JEIDescription description : JEICompatRegistry.descriptions)
        {
            List<ItemStack> items = description.items.stream()
                                                     .map(WrappedItemStack::getItemStack)
                                                     .map(ItemStack::copy)
                                                     .collect(Collectors.toList());

            registry.addDescription(items, description.desc);
        }
    }

    @SuppressWarnings("unchecked")
    private void addCraftingRecipes(IModRegistry registry, IJeiHelpers jeiHelpers)
    {
        for (JEICraftingRecipe recipe : JEICompatRegistry.craftingRecipes)
        {
            String uid = recipe.getUid();
            CraftingRecipeCategory category = new CraftingRecipeCategory(recipe, jeiHelpers.getGuiHelper());

            registry.addRecipeHandlers(new ShapedRecipeHandler(JEICompatRegistry.getShapedCraftingRecipeClass(recipe.recipeList), uid, jeiHelpers));
            registry.addRecipeHandlers(new ShapelessRecipeHandler(JEICompatRegistry.getShapelessCraftingRecipeClass(recipe.recipeList), uid, jeiHelpers));
            registry.addRecipeCategories(category);
            registry.addRecipes(CraftingManagerCS4.getRecipes(recipe.recipeList));

            addCommonEntries(registry, recipe, category.getModuleName(), category.getModule().rows * category.getModule().columns);
        }
    }

    @SuppressWarnings("unchecked")
    private void addMachineRecipes(IModRegistry registry, IJeiHelpers jeiHelpers)
    {
        for (JEIMachineRecipe recipe : JEICompatRegistry.machineRecipes)
        {
            String uid = recipe.getUid();
            MachineRecipeCategory category = new MachineRecipeCategory(recipe, jeiHelpers.getGuiHelper());

            registry.addRecipeHandlers(new MachineRecipeHandler((Class<MachineRecipeImpl>) JEICompatRegistry.getMachineRecipeClass(recipe.recipeList), uid, jeiHelpers));
            registry.addRecipeCategories(category);
            registry.addRecipes(MachineManager.getRecipes(recipe.recipeList));

            addCommonEntries(registry, recipe, category.getModuleName(), category.getModule().inputSlots);
        }
    }

    private void addCommonEntries(IModRegistry registry, JEIRecipe recipe, String moduleName, int inputSlots)
    {
        if (recipe.icon != null)
        {
            registry.addRecipeCategoryCraftingItem(recipe.icon.getItemStack(), recipe.getUid());
        }

        if (recipe.recipeAreaWidth > 0 && recipe.recipeAreaHeight > 0)
        {
            registry.addRecipeClickArea(recipe.getGui().getGuiClass(),
                                        recipe.recipeAreaX,
                                        recipe.recipeAreaY,
                                        recipe.recipeAreaWidth,
                                        recipe.recipeAreaHeight,
                                        recipe.getUid());
        }

        registry.getRecipeTransferRegistry().addRecipeTransferHandler(new TransferInfo(recipe.getUid(), moduleName, inputSlots));
    }
}
