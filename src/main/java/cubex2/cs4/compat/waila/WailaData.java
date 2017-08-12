package cubex2.cs4.compat.waila;

import com.google.common.collect.Lists;

import java.util.List;

public class WailaData
{
    static final List<Class> stackProviderBlocks = Lists.newArrayList();

    public static void registerStackProviderBlock(Class blockClass)
    {
        stackProviderBlocks.add(blockClass);
    }
}
