package cubex2.cs4.compat.jei;

import cubex2.cs4.plugins.jei.JEICompatRegistry;
import cubex2.cs4.plugins.jei.JEICraftingRecipe;
import cubex2.cs4.plugins.jei.JEIMachineRecipe;
import cubex2.cs4.plugins.jei.JEIRecipe;
import cubex2.cs4.plugins.vanilla.DamageableShapedOreRecipe;
import cubex2.cs4.plugins.vanilla.DamageableShapelessOreRecipe;
import cubex2.cs4.plugins.vanilla.MachineRecipeImpl;
import cubex2.cs4.plugins.vanilla.crafting.CraftingManagerCS4;
import cubex2.cs4.plugins.vanilla.crafting.MachineManager;
import mezz.jei.api.BlankModPlugin;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeWrapperFactory;

@JEIPlugin
public class CS4JEIPlugin extends BlankModPlugin
{
    @Override
    public void register(IModRegistry registry)
    {
        IJeiHelpers jeiHelpers = registry.getJeiHelpers();

        addMachineRecipes(registry, jeiHelpers);
        addCraftingRecipes(registry, jeiHelpers);
    }

    @SuppressWarnings("unchecked")
    private void addCraftingRecipes(IModRegistry registry, IJeiHelpers jeiHelpers)
    {
        IRecipeWrapperFactory<DamageableShapedOreRecipe> shapedFactory = recipe -> new ShapedRecipeWrapper(recipe, jeiHelpers);
        IRecipeWrapperFactory<DamageableShapelessOreRecipe> shapelessFactory = recipe -> new ShapelessRecipeWraper(recipe, jeiHelpers);
        for (JEICraftingRecipe recipe : JEICompatRegistry.craftingRecipes)
        {
            String uid = recipe.getUid();
            CraftingRecipeCategory category = new CraftingRecipeCategory(recipe, jeiHelpers.getGuiHelper());

            registry.handleRecipes(JEICompatRegistry.getShapedCraftingRecipeClass(recipe.recipeList), shapedFactory, uid);
            registry.handleRecipes(JEICompatRegistry.getShapelessCraftingRecipeClass(recipe.recipeList), shapelessFactory, uid);
            registry.addRecipeCategories(category);
            registry.addRecipes(CraftingManagerCS4.getRecipes(recipe.recipeList), uid);

            addCommonEntries(registry, recipe, category.getModuleName(), category.getModule().rows * category.getModule().columns);
        }
    }

    @SuppressWarnings("unchecked")
    private void addMachineRecipes(IModRegistry registry, IJeiHelpers jeiHelpers)
    {
        IRecipeWrapperFactory<MachineRecipeImpl> factory = recipe -> new MachineRecipeWrapper(recipe, jeiHelpers);

        for (JEIMachineRecipe recipe : JEICompatRegistry.machineRecipes)
        {
            String uid = recipe.getUid();
            MachineRecipeCategory category = new MachineRecipeCategory(recipe, jeiHelpers.getGuiHelper());

            registry.handleRecipes((Class<MachineRecipeImpl>) JEICompatRegistry.getMachineRecipeClass(recipe.recipeList), factory, uid);
            registry.addRecipeCategories(category);
            registry.addRecipes(MachineManager.getRecipes(recipe.recipeList), uid);

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
