package cubex2.cs4.plugins.vanilla;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class GuiHandler implements IGuiHandler
{
    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        ContentGuiBase content = GuiRegistry.get(ID);

        if (content != null)
        {
            return content.getServerGuiElement(player, world, x, y, z);
        }

        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        ContentGuiBase content = GuiRegistry.get(ID);

        if (content != null)
        {
            return content.getClientGuiElement(player, world, x, y, z);
        }

        return null;
    }
}
