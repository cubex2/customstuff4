package cubex2.cs4.mixin;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;

public class Mixin implements Opcodes
{
    private static Method defineClass;

    public static Class<?> create(String name, Class<?> baseClass, Class<?>... mixins)
    {
        checkArgument(Arrays.stream(mixins).noneMatch(Class::isInterface), "Interfaces can't be mixed in.");

        ClassNode baseNode = createClassNode(baseClass);
        List<ClassNode> mixinNodes = Arrays.stream(mixins).map(Mixin::createClassNode).collect(Collectors.toList());

        String oldName = baseNode.name;
        baseNode.name = name;

        baseNode.methods.forEach(m -> fixMethodInstructions(oldName, name, m));

        mixinNodes.forEach(mixin -> mixin(baseNode, mixin));

        return createClass(baseNode);
    }

    static void mixin(ClassNode baseNode, ClassNode mixin)
    {
        mixinFields(baseNode, mixin);
        mixinMethods(baseNode, mixin);
    }

    static void mixinFields(ClassNode base, ClassNode mixin)
    {
        for (FieldNode field : mixin.fields)
        {
            if (!hasField(base, field.name))
            {
                mixinField(base, field);
            }
        }
    }

    private static void mixinField(ClassNode base, FieldNode field)
    {
        base.fields.add(new FieldNode(field.access, field.name, field.desc, field.signature, field.value));
    }

    static boolean hasField(ClassNode node, String name)
    {
        return node.fields.stream().anyMatch(f -> f.name.equals(name));
    }

    static void mixinMethods(ClassNode base, ClassNode mixin)
    {
        for (MethodNode method : mixin.methods)
        {
            if (canMixinMethod(base, method))
            {
                mixinMethod(base, mixin, method);
            }
        }
    }

    private static void mixinMethod(ClassNode base, ClassNode mixin, MethodNode method)
    {
        MethodNode m = new MethodNode(method.access, method.name, method.desc, method.signature, method.exceptions.stream().toArray(String[]::new));
        m.instructions.add(method.instructions);

        fixMethodInstructions(mixin.name, base.name, m);

        base.methods.add(m);
    }

    private static void fixMethodInstructions(String oldName, String newName, MethodNode method)
    {
        ListIterator<AbstractInsnNode> iterator = method.instructions.iterator();
        while (iterator.hasNext())
        {
            AbstractInsnNode next = iterator.next();
            if (next instanceof FieldInsnNode)
            {
                FieldInsnNode field = (FieldInsnNode) next;
                if (field.getOpcode() == GETFIELD || field.getOpcode() == PUTFIELD)
                {
                    if (field.owner.equals(oldName))
                    {
                        field.owner = newName;
                    }
                }
            } else if (next instanceof MethodInsnNode)
            {
                MethodInsnNode methodNode = (MethodInsnNode) next;
                if (methodNode.getOpcode() == INVOKEVIRTUAL)
                {
                    if (methodNode.owner.equals(oldName))
                    {
                        methodNode.owner = newName;
                    }
                }
            }
        }
    }

    private static boolean canMixinMethod(ClassNode node, MethodNode method)
    {
        if (hasMethod(node, method.name))
            return false;
        if ((method.access & Opcodes.ACC_ABSTRACT) != 0)
            return false;
        if ((method.access & Opcodes.ACC_STATIC) != 0)
            return false;
        if (method.name.equals("<init>"))
            return false;

        return true;
    }

    static boolean hasMethod(ClassNode node, String name)
    {
        return node.methods.stream().anyMatch(m -> m.name.equals(name));
    }

    static ClassNode createClassNode(Class<?> clazz)
    {
        ClassNode node = new ClassNode();
        try
        {
            ClassReader reader = new ClassReader(clazz.getName());
            reader.accept(node, 0);

            return node;
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    static Class<?> createClass(ClassNode node)
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
            return (Class<?>) defineClass.invoke(Mixin.class.getClassLoader(), name, bytes, 0, bytes.length);
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
