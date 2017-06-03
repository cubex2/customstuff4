package cubex2.cs4.plugins.jei;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import cubex2.cs4.plugins.vanilla.DamageableShapedOreRecipe;
import cubex2.cs4.plugins.vanilla.DamageableShapelessOreRecipe;
import cubex2.cs4.plugins.vanilla.MachineRecipeImpl;
import cubex2.cs4.util.AsmHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.RecipeSorter;
import org.objectweb.asm.Opcodes;

import java.util.List;
import java.util.Map;

public class JEICompatRegistry implements Opcodes
{
    public static final List<JEIMachineRecipe> machineRecipes = Lists.newArrayList();
    private static final Map<ResourceLocation, Class<? extends MachineRecipeImpl>> recipeClasses = Maps.newHashMap();

    public static final List<JEICraftingRecipe> craftingRecipes = Lists.newArrayList();
    private static final Map<ResourceLocation, Class<? extends DamageableShapedOreRecipe>> shapedCraftingRecipeClasses = Maps.newHashMap();
    private static final Map<ResourceLocation, Class<? extends DamageableShapelessOreRecipe>> shapelessCraftingRecipeClasses = Maps.newHashMap();


    public static void addMachineRecipe(JEIMachineRecipe recipe)
    {
        machineRecipes.add(recipe);
    }

    public static void addCraftingRecipe(JEICraftingRecipe recipe)
    {
        craftingRecipes.add(recipe);
    }

    public static Class<? extends MachineRecipeImpl> getMachineRecipeClass(ResourceLocation list)
    {
        return recipeClasses.computeIfAbsent(list, recipeList -> AsmHelper.createSubClass(MachineRecipeImpl.class, recipeList.toString(), 0));
    }

    @SuppressWarnings("unchecked")
    public static Class<DamageableShapedOreRecipe> getShapedCraftingRecipeClass(ResourceLocation list)
    {
        return (Class<DamageableShapedOreRecipe>) shapedCraftingRecipeClasses.computeIfAbsent(list, recipeList ->
        {
            Class<? extends DamageableShapedOreRecipe> clazz = AsmHelper.createSubClass(DamageableShapedOreRecipe.class, recipeList.toString(), 3);
            RecipeSorter.register("customstuff4:shapedore:" + list.toString(), clazz, RecipeSorter.Category.SHAPED, "after:minecraft:shaped before:minecraft:shapeless");
            return clazz;
        });
    }

    @SuppressWarnings("unchecked")
    public static Class<DamageableShapelessOreRecipe> getShapelessCraftingRecipeClass(ResourceLocation list)
    {
        return (Class<DamageableShapelessOreRecipe>) shapelessCraftingRecipeClasses.computeIfAbsent(list, recipeList ->
        {
            Class<? extends DamageableShapelessOreRecipe> clazz = AsmHelper.createSubClass(DamageableShapelessOreRecipe.class, recipeList.toString(), 3);
            RecipeSorter.register("customstuff4:shapelessore:" + list.toString(), clazz, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
            return clazz;
        });
    }
}
