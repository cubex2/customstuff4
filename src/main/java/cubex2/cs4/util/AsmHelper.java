package cubex2.cs4.util;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

import java.lang.reflect.Method;

public class AsmHelper
{
    private static Method defineClass;

    public static Class<?> createClass(ClassNode node)
    {
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        node.accept(writer);

        byte[] bytes = writer.toByteArray();
        return createClassFromBytes(node.name.replace('/', '.'), bytes);
    }

    private static Class<?> createClassFromBytes(String name, byte[] bytes)
    {
        if (defineClass == null)
        {
            defineClass = getDefineClassMethod();
        }

        try
        {
            return (Class<?>) defineClass.invoke(AsmHelper.class.getClassLoader(), name, bytes, 0, bytes.length);
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
}
