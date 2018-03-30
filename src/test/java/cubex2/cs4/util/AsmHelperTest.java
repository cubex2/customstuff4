package cubex2.cs4.util;

import org.junit.jupiter.api.Test;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import static org.junit.jupiter.api.Assertions.*;

public class AsmHelperTest implements Opcodes
{

    @Test
    public void test_findConstructor()
    {
        ClassNode node = AsmHelper.createClassNode(TestClass.class);
        MethodNode c0 = AsmHelper.findConstructor(node, 0);
        MethodNode c3 = AsmHelper.findConstructor(node, 3);

        assertNotNull(c0);
        assertNotNull(c3);
    }

    @Test
    public void test_createSubClass() throws NoSuchMethodException
    {
        Class<? extends TestClass> class0 = AsmHelper.createSubClass(TestClass.class, "a", 0);
        Class<? extends TestClass> class3 = AsmHelper.createSubClass(TestClass.class, "b", 3);

        assertNotNull(class0.getConstructor());
        assertNotNull(class3.getConstructor(int.class, String.class, int[].class));

        assertTrue(TestClass.class.isAssignableFrom(class0));
        assertTrue(TestClass.class.isAssignableFrom(class3));
    }

    @Test
    public void test_createLoadOpcodes()
    {
        ClassNode node = AsmHelper.createClassNode(TestClass.class);
        MethodNode c0 = AsmHelper.findConstructor(node, 0);
        MethodNode c3 = AsmHelper.findConstructor(node, 3);
        MethodNode c6 = AsmHelper.findConstructor(node, 6);

        int[] opcodes0 = AsmHelper.createLoadOpcodes(c0);
        int[] opcodes3 = AsmHelper.createLoadOpcodes(c3);
        int[] opcodes6 = AsmHelper.createLoadOpcodes(c6);

        assertArrayEquals(new int[] {ALOAD}, opcodes0);
        assertArrayEquals(new int[] {ALOAD, ILOAD, ALOAD, ALOAD}, opcodes3);
        assertArrayEquals(new int[] {ALOAD, ILOAD, FLOAD, DLOAD, ILOAD, LLOAD, ALOAD}, opcodes6);
    }

    public static class TestClass
    {
        public TestClass()
        {
        }

        public TestClass(int a, String b, int[] c)
        {

        }

        public TestClass(int a, float b, double c, boolean d, long e, Integer f)
        {

        }
    }
}