package cubex2.cs4.mixin;

import cubex2.cs4.util.AsmHelper;
import org.junit.Test;
import org.objectweb.asm.tree.ClassNode;

import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.*;

public class MixinTests
{
    @Test
    public void test_create() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException
    {
        Class<?> newClass = Mixin.create("cubex2/cs4/mixin/NewClass", BaseClass.class, MixinClass.class, MixinClass2.class);
        Object instance = newClass.newInstance();

        testField = -1;

        newClass.getMethod("newMethod").invoke(instance);
        assertEquals(1, testField);
        assertEquals(5, newClass.getField("parentField").getInt(instance));

        newClass.getMethod("baseMethod").invoke(instance);
        assertEquals(1, testField);
    }

    @Test
    public void test_create_overrideMethod() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException
    {
        Class<?> newClass = Mixin.create("cubex2/cs4/mixin/NewClass1", BaseClass.class, MixinClassOverride.class);
        Object instance = newClass.newInstance();

        newClass.getMethod("parentMethod").invoke(instance);

        assertEquals(15, newClass.getField("parentField").getInt(instance));
    }

    @Test
    public void test_createClassNode()
    {
        ClassNode node = Mixin.createClassNode(BaseClass.class);

        assertEquals("cubex2/cs4/mixin/MixinTests$BaseClass", node.name);
    }

    @Test
    public void test_createClass()
    {
        ClassNode node = Mixin.createClassNode(BaseClass.class);
        node.name = node.name.replace("MixinTests$BaseClass", "CreatedClass");
        Class<?> clazz = AsmHelper.createClass(node);

        assertEquals("cubex2.cs4.mixin.CreatedClass", clazz.getName());
    }

    @Test
    public void test_mixin()
    {
        ClassNode base = Mixin.createClassNode(BaseClass.class);
        ClassNode mixin = Mixin.createClassNode(MixinClass.class);

        Mixin.mixin(base, mixin);

        assertEquals(3, base.methods.size());
        assertEquals(3, base.fields.size());
    }

    @Test
    public void test_hasField()
    {
        ClassNode node = Mixin.createClassNode(MixinClass.class);

        assertTrue(Mixin.hasField(node, "field"));
        assertFalse(Mixin.hasField(node, "fieldA"));
    }

    @Test
    public void test_mixinFields()
    {
        ClassNode base = Mixin.createClassNode(BaseClass.class);
        ClassNode mixin = Mixin.createClassNode(MixinClass.class);

        Mixin.mixinFields(base, mixin);

        assertEquals(3, base.fields.size());
         assertEquals("baseField", base.fields.get(0).name);
        assertEquals("I", base.fields.get(0).desc);
        assertEquals("field", base.fields.get(1).name);
        assertEquals("I", base.fields.get(1).desc);
        assertEquals("field2", base.fields.get(2).name);
        assertEquals("Ljava/lang/String;", base.fields.get(2).desc);
    }

    @Test
    public void test_hasMethod()
    {
        ClassNode mixin = Mixin.createClassNode(MixinClass.class);

        assertTrue(Mixin.hasMethod(mixin, "newMethod"));
        assertFalse(Mixin.hasMethod(mixin, "notNewMethod"));
    }

    @Test
    public void test_mixinMethods()
    {
        ClassNode base = Mixin.createClassNode(BaseClass.class);
        ClassNode mixin = Mixin.createClassNode(MixinClass.class);

        Mixin.mixinMethods(base, mixin);

        assertEquals(3, base.methods.size());
        assertTrue(Mixin.hasMethod(base, "newMethod"));
    }

    @Test
    public void test_callPrivateMethodInConstructor() throws IllegalAccessException, InstantiationException
    {
        Class<?> aClass = Mixin.create("cubex2/cs4/mixin/NewClass2", ConstructorTest.class);
        aClass.newInstance();
    }

    static int testField;

    public static class ParentClass
    {
        public int parentField;

        public void parentMethod()
        {
            parentField = 5;
        }
    }

    public static class BaseClass extends ParentClass
    {
        private int baseField;

        public BaseClass()
        {
            baseField = 5;
        }

        public void baseMethod()
        {

        }
    }

    private abstract static class MixinClass extends ParentClass
    {
        private int field;
        private String field2;

        public void baseMethod()
        {

        }

        public void newMethod()
        {
            field = 5;
            field2 = "ABC";
            testField = 1;
            baseMethod();
            parentMethod();
        }

        abstract void abstractMethod();

        static void staticMethod()
        {

        }
    }

    private abstract static class MixinClass2 extends ParentClass
    {
        private int field;
        private String field2;

        public void baseMethod()
        {
            testField = 5;
        }

        public void newMethod()
        {
            testField = 2;
        }
    }

    private abstract static class MixinClassOverride extends ParentClass
    {
        @Override
        public void parentMethod()
        {
            super.parentMethod();
            parentField += 10;
        }
    }

    private static class ConstructorTest
    {
        public ConstructorTest()
        {
            method();
        }

        private void method()
        {

        }
    }
}
