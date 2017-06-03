package cubex2.cs4.compat.jei;

import cubex2.cs4.plugins.jei.JEICompatRegistry;
import cubex2.cs4.plugins.jei.JEICraftingRecipe;
import cubex2.cs4.plugins.jei.JEIMachineRecipe;
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
    @SuppressWarnings("unchecked")
    @Override
    public void register(IModRegistry registry)
    {
        IJeiHelpers jeiHelpers = registry.getJeiHelpers();
        IRecipeWrapperFactory<MachineRecipeImpl> factory = new MachineRecipeWrapperFactory(jeiHelpers);

        for (JEIMachineRecipe recipe : JEICompatRegistry.machineRecipes)
        {
            String recipeUid = recipe.getRecipeUid();

            registry.handleRecipes((Class<MachineRecipeImpl>) JEICompatRegistry.getMachineRecipeClass(recipe.recipeList), factory, recipeUid);
            registry.addRecipeCategories(new MachineRecipeCategory(recipe, jeiHelpers.getGuiHelper()));
            registry.addRecipes(MachineManager.getRecipes(recipe.recipeList), recipeUid);

            if (recipe.icon != null)
            {
                registry.addRecipeCategoryCraftingItem(recipe.icon.getItemStack(), recipeUid);
            }

            if (recipe.recipeAreaWidth > 0 && recipe.recipeAreaHeight > 0)
            {
                registry.addRecipeClickArea(recipe.getGui().getGuiClass(),
                                            recipe.recipeAreaX,
                                            recipe.recipeAreaY,
                                            recipe.recipeAreaWidth,
                                            recipe.recipeAreaHeight,
                                            recipeUid);
            }
        }

        ShapedCraftingRecipeWrapperFactory shapedFactory = new ShapedCraftingRecipeWrapperFactory(jeiHelpers);
        ShapelessCraftingRecipeWrapperFactory shapelessFactory = new ShapelessCraftingRecipeWrapperFactory(jeiHelpers);
        for (JEICraftingRecipe recipe : JEICompatRegistry.craftingRecipes)
        {
            String uid = recipe.getRecipeUid();
            registry.handleRecipes((Class<DamageableShapedOreRecipe>) JEICompatRegistry.getShapedCraftingRecipeClass(recipe.recipeList), shapedFactory, uid);
            registry.handleRecipes((Class<DamageableShapelessOreRecipe>) JEICompatRegistry.getShapelessCraftingRecipeClass(recipe.recipeList), shapelessFactory, uid);
            registry.addRecipeCategories(new CraftingRecipeCategory(recipe, jeiHelpers.getGuiHelper()));
            registry.addRecipes(CraftingManagerCS4.getRecipes(recipe.recipeList), uid);

            if (recipe.icon != null)
            {
                registry.addRecipeCategoryCraftingItem(recipe.icon.getItemStack(), uid);
            }

            if (recipe.recipeAreaWidth > 0 && recipe.recipeAreaHeight > 0)
            {
                registry.addRecipeClickArea(recipe.getGui().getGuiClass(),
                                            recipe.recipeAreaX,
                                            recipe.recipeAreaY,
                                            recipe.recipeAreaWidth,
                                            recipe.recipeAreaHeight,
                                            uid);
            }
        }
    }
}
