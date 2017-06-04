package cubex2.cs4.util;

import org.objectweb.asm.*;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import java.io.IOException;
import java.lang.reflect.Method;

public class AsmHelper implements Opcodes
{
    private static Method defineClass;

    public static Class<?> createClass(ClassNode node)
    {
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        node.accept(writer);

        byte[] bytes = writer.toByteArray();
        return createClassFromBytes(node.name.replace('/', '.'), bytes);
    }

    public static Class<?> createClassFromBytes(String name, byte[] bytes)
    {
        if (defineClass == null)
        {
            defineClass = getDefineClassMethod();
        }

        try
        {
            return (Class<?>) defineClass.invoke(AsmHelper.class.getClassLoader(), name.replace('/', '.'), bytes, 0, bytes.length);
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    private static Method getDefineClassMethod()
    {
        Method defineClass = null;

        try
        {
            defineClass = ClassLoader.class.getDeclaredMethod("defineClass", String.class, byte[].class, int.class, int.class);
            defineClass.setAccessible(true);
        } catch (NoSuchMethodException e)
        {
            e.printStackTrace();
        }

        return defineClass;
    }

    public static <T> Class<? extends T> createSubClass(Class<T> superClass, String nameSuffix, int constructorParams)
    {
        ClassNode superNode = createClassNode(superClass);
        MethodNode constructor = findConstructor(superNode, constructorParams);
        String className = superClass.getName().replace('.', '/') + "_" + nameSuffix.replace(":", "_");

        ClassWriter cw = new ClassWriter(0);
        cw.visit(V1_7, ACC_PUBLIC + ACC_SUPER, className, null, Type.getInternalName(superClass), null);

        // Constructor
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, constructor.name, constructor.desc, null, null);
        int[] opcodes = createLoadOpcodes(constructor);
        for (int i = 0; i < opcodes.length; i++)
        {
            mv.visitVarInsn(opcodes[i], i);
        }
        mv.visitMethodInsn(INVOKESPECIAL, Type.getInternalName(superClass), constructor.name, constructor.desc, false);
        mv.visitInsn(RETURN);
        mv.visitMaxs(constructorParams + 1, constructorParams + 1);
        mv.visitEnd();

        byte[] byteCode = cw.toByteArray();

        return (Class<? extends T>) createClassFromBytes(className, byteCode);
    }

    static int[] createLoadOpcodes(MethodNode method)
    {
        Type[] argumentTypes = Type.getArgumentTypes(method.desc);

        int[] opcodes = new int[argumentTypes.length + 1];
        opcodes[0] = ALOAD;

        for (int i = 0; i < argumentTypes.length; i++)
        {
            opcodes[i + 1] = argumentTypes[i].getOpcode(ILOAD);
        }

        return opcodes;
    }

    static MethodNode findConstructor(ClassNode node, int numParams)
    {
        for (MethodNode method : node.methods)
        {
            Type[] argumentTypes = Type.getArgumentTypes(method.desc);
            if (method.name.equals("<init>") && argumentTypes.length == numParams)
            {
                return method;
            }
        }

        return null;
    }

    public static ClassNode createClassNode(Class<?> clazz)
    {
        ClassNode node = new ClassNode();
        try
        {
            String fileName = clazz.getName().replace('.', '/') + ".class";
            ClassReader reader = new ClassReader(clazz.getClassLoader().getResourceAsStream(fileName));
            reader.accept(node, 0);

            return node;
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        throw new RuntimeException("Couldn't create ClassNode for class " + clazz.getName());
    }
}
