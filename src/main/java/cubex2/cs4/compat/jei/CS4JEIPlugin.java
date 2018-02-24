package cubex2.cs4.compat.jei;

import com.google.common.collect.Lists;
import cubex2.cs4.api.WrappedItemStack;
import cubex2.cs4.plugins.jei.*;
import cubex2.cs4.plugins.vanilla.DamageableShapedOreRecipe;
import cubex2.cs4.plugins.vanilla.DamageableShapelessOreRecipe;
import cubex2.cs4.plugins.vanilla.crafting.CraftingManagerCS4;
import cubex2.cs4.plugins.vanilla.crafting.MachineManager;
import cubex2.cs4.plugins.vanilla.crafting.MachineRecipe;
import cubex2.cs4.plugins.vanilla.crafting.MachineRecipeOutput;
import cubex2.cs4.util.ReflectionHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.IRecipeWrapperFactory;
import net.minecraft.item.ItemStack;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@JEIPlugin
public class CS4JEIPlugin implements IModPlugin
{
    private List<CraftingRecipeCategory> craftingCategories = Lists.newArrayList();
    private List<MachineRecipeCategory> machineCategories = Lists.newArrayList();

    @Override
    public void register(IModRegistry registry)
    {
        IJeiHelpers jeiHelpers = registry.getJeiHelpers();

        addMachineRecipes(registry, jeiHelpers);
        addCraftingRecipes(registry, jeiHelpers);
        addDescriptions(registry);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry)
    {
        IJeiHelpers jeiHelpers = registry.getJeiHelpers();

        for (JEICraftingRecipe recipe : JEICompatRegistry.craftingRecipes)
        {
            CraftingRecipeCategory category = new CraftingRecipeCategory(recipe, jeiHelpers.getGuiHelper());
            registry.addRecipeCategories(category);
            craftingCategories.add(category);
        }

        for (JEIMachineRecipe recipe : JEICompatRegistry.machineRecipes)
        {
            MachineRecipeCategory category = new MachineRecipeCategory(recipe, jeiHelpers.getGuiHelper());
            registry.addRecipeCategories(category);
            machineCategories.add(category);
        }
    }

    @SuppressWarnings("unchecked")
    private void addCraftingRecipes(IModRegistry registry, IJeiHelpers jeiHelpers)
    {
        IRecipeWrapperFactory<DamageableShapedOreRecipe> shapedFactory = recipe -> new ShapedRecipeWrapper(recipe, jeiHelpers);
        IRecipeWrapperFactory<DamageableShapelessOreRecipe> shapelessFactory = recipe -> new ShapelessRecipeWrapper(recipe, jeiHelpers);
        for (CraftingRecipeCategory category : craftingCategories)
        {
            JEICraftingRecipe recipe = category.getRecipe();
            String uid = recipe.getUid();

            registry.handleRecipes(JEICompatRegistry.getShapedCraftingRecipeClass(recipe.recipeList), shapedFactory, uid);
            registry.handleRecipes(JEICompatRegistry.getShapelessCraftingRecipeClass(recipe.recipeList), shapelessFactory, uid);
            registry.addRecipes(CraftingManagerCS4.getRecipes(recipe.recipeList), uid);

            addCommonEntries(registry, recipe, category.getModuleName(), category.getModule().rows * category.getModule().columns);
        }
    }

    @SuppressWarnings("unchecked")
    private void addMachineRecipes(IModRegistry registry, IJeiHelpers jeiHelpers)
    {
        IRecipeWrapperFactory<DelegatedMachineRecipe> factory = recipe -> new MachineRecipeWrapper(recipe.recipe, recipe.usedOutput, jeiHelpers);

        for (MachineRecipeCategory category : machineCategories)
        {
            JEIMachineRecipe recipe = category.getRecipe();
            String uid = recipe.getUid();

            Class<DelegatedMachineRecipe> delegatedRecipeClass = (Class<DelegatedMachineRecipe>) JEICompatRegistry.getDelegatedMachineRecipeClass(recipe.recipeList);
            registry.handleRecipes(delegatedRecipeClass, factory, uid);
            for (MachineRecipe machineRecipe : MachineManager.getRecipes(recipe.recipeList))
            {
                // We want each output to show as its own recipe
                for (MachineRecipeOutput output : machineRecipe.getOutputs())
                {
                    DelegatedMachineRecipe delegatedRecipe = ReflectionHelper.newInstance(delegatedRecipeClass);
                    delegatedRecipe.recipe = machineRecipe;
                    delegatedRecipe.usedOutput = output;
                    registry.addRecipes(Collections.singletonList(delegatedRecipe), uid);
                }
            }

            addCommonEntries(registry, recipe, category.getModuleName(), category.getModule().inputSlots);
        }
    }

    private void addCommonEntries(IModRegistry registry, JEIRecipe recipe, String moduleName, int inputSlots)
    {
        if (recipe.icon != null)
        {
            registry.addRecipeCatalyst(recipe.icon.getItemStack(), recipe.getUid());
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

    private void addDescriptions(IModRegistry registry)
    {
        for (JEIDescription description : JEICompatRegistry.descriptions)
        {
            List<ItemStack> items = description.items.stream()
                                                     .map(WrappedItemStack::getItemStack)
                                                     .map(ItemStack::copy)
                                                     .collect(Collectors.toList());
            registry.addIngredientInfo(items, ItemStack.class, description.desc);
        }
    }
}
