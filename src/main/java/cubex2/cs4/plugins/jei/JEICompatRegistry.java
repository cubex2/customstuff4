package cubex2.cs4.plugins.jei;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import cubex2.cs4.plugins.vanilla.MachineRecipeImpl;
import cubex2.cs4.util.AsmHelper;
import net.minecraft.util.ResourceLocation;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
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
        return recipeClasses.computeIfAbsent(list, JEICompatRegistry::createClass);
    }

    @SuppressWarnings("unchecked")
    private static Class<? extends MachineRecipeImpl> createClass(ResourceLocation recipeList)
    {
        Class<? extends MachineRecipeImpl> template = MachineRecipeImpl.class;
        String className = template.getName().replace('.', '/') + "_" + recipeList.toString().replace(':', '_');

        byte[] byteCode = createByteCode(template, className);
        return (Class<? extends MachineRecipeImpl>) AsmHelper.createClassFromBytes(className, byteCode);
    }

    private static byte[] createByteCode(Class<? extends MachineRecipeImpl> baseClass, String className)
    {
        ClassWriter cw = new ClassWriter(0);
        cw.visit(V1_7, ACC_PUBLIC + ACC_SUPER, className, null, org.objectweb.asm.Type.getInternalName(baseClass), null);

        // Constructor
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKESPECIAL, org.objectweb.asm.Type.getInternalName(baseClass), "<init>", "()V", false);
        mv.visitInsn(RETURN);
        mv.visitMaxs(1, 1);
        mv.visitEnd();

        return cw.toByteArray();
    }
}
