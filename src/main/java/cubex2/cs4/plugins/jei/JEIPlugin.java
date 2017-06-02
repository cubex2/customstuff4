package cubex2.cs4.plugins.jei;

import cubex2.cs4.api.CS4Plugin;
import cubex2.cs4.api.ContentRegistry;
import cubex2.cs4.api.CustomStuffPlugin;

@CS4Plugin
public class JEIPlugin implements CustomStuffPlugin
{
    @Override
    public void registerContent(ContentRegistry registry)
    {
        registry.registerContentType("jei:machineRecipe", JEIMachineRecipe.class);
    }
}
