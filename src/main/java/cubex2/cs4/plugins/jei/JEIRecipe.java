package cubex2.cs4.plugins.jei;

import cubex2.cs4.api.WrappedItemStack;
import cubex2.cs4.data.SimpleContent;
import cubex2.cs4.plugins.vanilla.ContentGuiContainer;
import cubex2.cs4.plugins.vanilla.GuiRegistry;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public abstract class JEIRecipe extends SimpleContent
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
    public int recipeAreaX;
    public int recipeAreaY;
    public int recipeAreaWidth = -1;
    public int recipeAreaHeight = -1;
    public int transferButtonX = -1;
    public int transferButtonY = -1;

    @SuppressWarnings("unchecked")
    @Nullable
    public ContentGuiContainer getGui()
    {
        return (ContentGuiContainer) GuiRegistry.get(gui);
    }

    public abstract String getUid();
}
