package cubex2.cs4.plugins.jei;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import cubex2.cs4.compat.jei.DelegatedMachineRecipe;
import cubex2.cs4.plugins.vanilla.DamageableShapedOreRecipe;
import cubex2.cs4.plugins.vanilla.DamageableShapelessOreRecipe;
import cubex2.cs4.util.AsmHelper;
import net.minecraft.util.ResourceLocation;
import org.objectweb.asm.Opcodes;

import java.util.List;
import java.util.Map;

public class JEICompatRegistry implements Opcodes
{
    public static final List<JEIMachineRecipe> machineRecipes = Lists.newArrayList();
    private static final Map<ResourceLocation, Class<? extends DelegatedMachineRecipe>> delegatedRecipeClasses = Maps.newHashMap();

    public static final List<JEICraftingRecipe> craftingRecipes = Lists.newArrayList();
    private static final Map<ResourceLocation, Class<? extends DamageableShapedOreRecipe>> shapedCraftingRecipeClasses = Maps.newHashMap();
    private static final Map<ResourceLocation, Class<? extends DamageableShapelessOreRecipe>> shapelessCraftingRecipeClasses = Maps.newHashMap();

    public static final List<JEIDescription> descriptions = Lists.newArrayList();

    public static void addMachineRecipe(JEIMachineRecipe recipe)
    {
        machineRecipes.add(recipe);
    }

    public static void addCraftingRecipe(JEICraftingRecipe recipe)
    {
        craftingRecipes.add(recipe);
    }

    public static void addDescription(JEIDescription description)
    {
        descriptions.add(description);
    }

    public static Class<? extends DelegatedMachineRecipe> getDelegatedMachineRecipeClass(ResourceLocation list)
    {
        return delegatedRecipeClasses.computeIfAbsent(list, recipeList -> AsmHelper.createSubClass(DelegatedMachineRecipe.class, recipeList.toString(), 0));
    }

    @SuppressWarnings("unchecked")
    public static Class<DamageableShapedOreRecipe> getShapedCraftingRecipeClass(ResourceLocation list)
    {
        return (Class<DamageableShapedOreRecipe>) shapedCraftingRecipeClasses.computeIfAbsent(list, recipeList ->
                AsmHelper.createSubClass(DamageableShapedOreRecipe.class, recipeList.toString(), 4));
    }

    @SuppressWarnings("unchecked")
    public static Class<DamageableShapelessOreRecipe> getShapelessCraftingRecipeClass(ResourceLocation list)
    {
        return (Class<DamageableShapelessOreRecipe>) shapelessCraftingRecipeClasses.computeIfAbsent(list, recipeList ->
                AsmHelper.createSubClass(DamageableShapelessOreRecipe.class, recipeList.toString(), 4));
    }
}
