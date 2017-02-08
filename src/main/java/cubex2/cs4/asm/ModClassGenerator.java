package cubex2.cs4.asm;

import cubex2.cs4.CustomStuff4;
import org.apache.commons.io.IOUtils;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.google.common.base.Preconditions.checkArgument;
import static org.objectweb.asm.Opcodes.*;

public class ModClassGenerator
{
    private static final String MOD_LOADER = "cubex2/cs4/ModLoader";
    private static final String CS4_MOD = "cubex2/cs4/CS4Mod";
    private static final String PRE_INIT_EVENT = "net/minecraftforge/fml/common/event/FMLPreInitializationEvent";
    private static final String INIT_EVENT = "net/minecraftforge/fml/common/event/FMLInitializationEvent";
    private static final String POST_INIT_EVENT = "net/minecraftforge/fml/common/event/FMLPostInitializationEvent";
    private static final String EVENT_HANDLER = "net/minecraftforge/fml/common/Mod$EventHandler";
    private static final String MOD = "net/minecraftforge/fml/common/Mod";

    public static void createModClass(File modFolder, ModInfo info)
    {
        File classFile = getClassFile(modFolder, info.id);
        if (!classFile.exists())
        {
            classFile.getParentFile().mkdirs();

            byte[] bytes = generateClassCode(info);
            writeBytesToFile(bytes, classFile);
        }
    }

    private static File getClassFile(File modFolder, String modId)
    {
        return new File(modFolder, "cs4mod/" + modId + ".class");
    }

    private static byte[] generateClassCode(ModInfo info)
    {
        checkArgument(info.isValid(), "Invalid mod id");

        ClassWriter cw = new ClassWriter(0);
        cw.visit(V1_7, ACC_PUBLIC + ACC_SUPER, "cs4mod/" + info.id, null, "java/lang/Object", new String[] {CS4_MOD});

        // Mod annotation
        AnnotationVisitor av = cw.visitAnnotation(String.format("L%s;", MOD), true);
        av.visit("modid", info.id);
        av.visit("name", info.name);
        av.visit("version", info.version);
        av.visit("dependencies", String.format("required-after:%s;%s", CustomStuff4.ID, info.dependencies));
        av.visitEnd();

        // Constructor
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
        mv.visitCode();
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V");
        mv.visitInsn(RETURN);
        mv.visitMaxs(1, 1);
        mv.visitEnd();

        // PreInit
        mv = cw.visitMethod(ACC_PUBLIC, "preInit", voidMethodDesc(PRE_INIT_EVENT), null, null);
        av = mv.visitAnnotation(String.format("L%s;", EVENT_HANDLER), true);
        av.visitEnd();
        mv.visitCode();
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKESTATIC, MOD_LOADER, "onPreInitMod", voidMethodDesc(CS4_MOD), false);
        mv.visitInsn(RETURN);
        mv.visitMaxs(1, 2);
        mv.visitEnd();

        // Init
        mv = cw.visitMethod(ACC_PUBLIC, "init", voidMethodDesc(INIT_EVENT), null, null);
        av = mv.visitAnnotation(String.format("L%s;", EVENT_HANDLER), true);
        av.visitEnd();
        mv.visitCode();
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKESTATIC, MOD_LOADER, "onInitMod", voidMethodDesc(CS4_MOD), false);
        mv.visitInsn(RETURN);
        mv.visitMaxs(1, 2);
        mv.visitEnd();

        // PostInit
        mv = cw.visitMethod(ACC_PUBLIC, "postInit", voidMethodDesc(POST_INIT_EVENT), null, null);
        av = mv.visitAnnotation(String.format("L%s;", EVENT_HANDLER), true);
        av.visitEnd();
        mv.visitCode();
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKESTATIC, MOD_LOADER, "onPostInitMod", voidMethodDesc(CS4_MOD), false);
        mv.visitInsn(RETURN);
        mv.visitMaxs(1, 2);
        mv.visitEnd();

        return cw.toByteArray();
    }

    private static String voidMethodDesc(String param)
    {
        return String.format("(L%s;)V", param);
    }

    private static void writeBytesToFile(byte[] bytes, File file)
    {
        FileOutputStream fos = null;
        try
        {
            fos = new FileOutputStream(file);
            fos.write(bytes);
        } catch (IOException e)
        {
            e.printStackTrace();
        } finally
        {
            if (fos != null)
                IOUtils.closeQuietly(fos);
        }
    }
}
