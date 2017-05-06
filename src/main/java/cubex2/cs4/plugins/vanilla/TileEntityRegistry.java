package cubex2.cs4.plugins.vanilla;

import com.google.common.collect.Maps;
import cubex2.cs4.util.AsmHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import javax.annotation.Nullable;
import java.util.Map;

public class TileEntityRegistry implements Opcodes
{
    private static final Map<ResourceLocation, Entry> map = Maps.newHashMap();

    public static void register(ContentTileEntityBase content)
    {
        Class<? extends TileEntity> clazz = createClass(content.getTemplateClass(), content.getKey().toString());
        map.put(content.getKey(), new Entry(clazz, content));
        GameRegistry.registerTileEntity(clazz, content.getKey().toString());
    }

    public static ContentTileEntityBase getContent(ResourceLocation id)
    {
        return map.get(id).content;
    }

    @Nullable
    public static TileEntity createTileEntity(ResourceLocation id)
    {
        try
        {
            return map.get(id).clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    static Class<? extends TileEntity> createClass(Class<? extends TileEntity> template, String contentId)
    {
        String className = template.getName().replace('.', '/') + "_" + contentId.replace(":", "_");

        byte[] byteCode = generateClass(template, className, contentId);
        return (Class<? extends TileEntity>) AsmHelper.createClassFromBytes(className, byteCode);
    }

    private static byte[] generateClass(Class<? extends TileEntity> baseClass, String className, String contentId)
    {
        ClassWriter cw = new ClassWriter(0);
        cw.visit(V1_7, ACC_PUBLIC + ACC_SUPER, className, null, Type.getInternalName(baseClass), null);

        // Constructor
        MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
        mv.visitVarInsn(ALOAD, 0);
        mv.visitLdcInsn(contentId);
        mv.visitMethodInsn(INVOKESPECIAL, Type.getInternalName(baseClass), "<init>", "(Ljava/lang/String;)V", false);
        mv.visitInsn(RETURN);
        mv.visitMaxs(2, 1);
        mv.visitEnd();

        return cw.toByteArray();
    }

    private static class Entry
    {
        final Class<? extends TileEntity> clazz;
        final ContentTileEntityBase content;

        public Entry(Class<? extends TileEntity> clazz, ContentTileEntityBase content)
        {
            this.clazz = clazz;
            this.content = content;
        }
    }
}
