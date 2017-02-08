package cubex2.cs4.util;

import com.google.common.collect.Lists;
import cubex2.cs4.api.CS4Plugin;
import cubex2.cs4.api.CustomStuffPlugin;
import net.minecraftforge.fml.common.discovery.ASMDataTable;

import java.util.List;
import java.util.Set;

public class PluginHelper
{
    public static List<CustomStuffPlugin> getPluginInstances(ASMDataTable asmDataTable)
    {
        String annotationName = CS4Plugin.class.getCanonicalName();
        Set<ASMDataTable.ASMData> asmDatas = asmDataTable.getAll(annotationName);

        List<CustomStuffPlugin> instances = Lists.newArrayList();
        for (ASMDataTable.ASMData asmData : asmDatas)
        {
            try
            {
                Class<?> asmClass = Class.forName(asmData.getClassName());
                if (CustomStuffPlugin.class.isAssignableFrom(asmClass))
                {
                    Class<? extends CustomStuffPlugin> instanceClass = asmClass.asSubclass(CustomStuffPlugin.class);
                    CustomStuffPlugin instance = instanceClass.newInstance();
                    instances.add(instance);
                }
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e)
            {
                e.printStackTrace();
            }
        }
        return instances;
    }
}
