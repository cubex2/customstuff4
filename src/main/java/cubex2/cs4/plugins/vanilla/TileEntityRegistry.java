package cubex2.cs4.plugins.vanilla;

import com.google.common.collect.Maps;
import cubex2.cs4.mixin.Mixin;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import javax.annotation.Nullable;
import java.util.Map;

public class TileEntityRegistry
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

        return (Class<? extends TileEntity>) Mixin.create(className, n -> addNameMethod(contentId, n), template);
    }

    private static void addNameMethod(String contentId, ClassNode node)
    {
        MethodNode m = new MethodNode(Opcodes.ACC_PUBLIC, "getContentId", "()Ljava/lang/String;", null, null);
        LabelNode start = new LabelNode(new Label());
        LabelNode end = new LabelNode(new Label());
        m.instructions.add(start);
        m.instructions.add(new LdcInsnNode(contentId));
        m.instructions.add(new InsnNode(Opcodes.ARETURN));
        m.instructions.add(end);
        m.maxStack = 1;
        m.maxLocals = 1;
        m.localVariables.add(new LocalVariableNode("this", "L" + node.name + ";", null, start, end, 0));

        node.methods.add(m);
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
