package cubex2.cs4.plugins.jei;

import cubex2.cs4.api.ContentHelper;
import cubex2.cs4.api.InitPhase;
import cubex2.cs4.api.WrappedItemStack;
import cubex2.cs4.data.SimpleContent;
import net.minecraft.util.ResourceLocation;

public class JEIMachineRecipe extends SimpleContent
{
    public ResourceLocation recipeList;
    public String title = "Unnamed";
    public ResourceLocation gui;
    public ResourceLocation tileEntity;
    public String module;
    public int bgX;
    public int bgY;
    public int bgWidth;
    public int bgHeight;
    public WrappedItemStack icon;

    @Override
    protected void doInit(InitPhase phase, ContentHelper helper)
    {
        JEICompatRegistry.addMachineRecipe(this);
    }

    @Override
    protected boolean isReady()
    {
        return true;
    }
}
