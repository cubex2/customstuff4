package cubex2.cs4.plugins.jei;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import cubex2.cs4.plugins.vanilla.MachineRecipeImpl;
import cubex2.cs4.util.AsmHelper;
import net.minecraft.util.ResourceLocation;
import org.objectweb.asm.Opcodes;

import java.util.List;
import java.util.Map;

public class JEICompatRegistry implements Opcodes
{
    public static final List<JEIMachineRecipe> machineRecipes = Lists.newArrayList();
    private static final Map<ResourceLocation, Class<? extends MachineRecipeImpl>> recipeClasses = Maps.newHashMap();

    public static void addMachineRecipe(JEIMachineRecipe recipe)
    {
        machineRecipes.add(recipe);
    }

    public static Class<? extends MachineRecipeImpl> getMachineRecipeClass(ResourceLocation list)
    {
        return recipeClasses.computeIfAbsent(list, recipeList -> AsmHelper.createSubClass(MachineRecipeImpl.class, recipeList.toString(), 0));
    }
}
