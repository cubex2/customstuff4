package cubex2.cs4.plugins.vanilla;

import com.google.common.collect.Maps;
import net.minecraft.client.gui.*;
import net.minecraft.client.multiplayer.GuiConnecting;

import javax.annotation.Nullable;
import java.util.Map;

class GuiMapping
{
    private static final Map<String, Class<? extends GuiScreen>> map = Maps.newHashMap();

    @Nullable
    static Class<? extends GuiScreen> getGuiClass(String name)
    {
        return map.get(name);
    }

    static
    {
        map.put("mainmenu", GuiMainMenu.class);
        map.put("ingamemenu", GuiIngameMenu.class);
        map.put("options", GuiOptions.class);
        map.put("multiplayer", GuiMultiplayer.class);
        map.put("connecting", GuiConnecting.class);
        map.put("downloadterrain", GuiDownloadTerrain.class);
        map.put("gameover", GuiGameOver.class);
    }
}
