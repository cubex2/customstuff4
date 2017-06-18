package cubex2.cs4;

import cubex2.cs4.api.CustomStuffPlugin;
import cubex2.cs4.data.ContentRegistryImpl;
import cubex2.cs4.plugins.vanilla.DamageableShapedOreRecipe;
import cubex2.cs4.plugins.vanilla.DamageableShapelessOreRecipe;
import cubex2.cs4.plugins.vanilla.EventHandler;
import cubex2.cs4.plugins.vanilla.GuiHandler;
import cubex2.cs4.plugins.vanilla.network.PacketSyncContainerFluid;
import cubex2.cs4.util.PluginHelper;
import net.minecraft.init.Bootstrap;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.oredict.RecipeSorter;

import java.io.File;
import java.util.List;

@Mod(modid = CustomStuff4.ID, name = CustomStuff4.NAME, version = CustomStuff4.VERSION)
public class CustomStuff4
{
    public static final String ID = "customstuff4";
    public static final String NAME = "Custom Stuff 4";
    public static final String VERSION = "0.0.8";

    public static final ContentRegistryImpl contentRegistry = new ContentRegistryImpl();

    @SidedProxy(clientSide = "cubex2.cs4.ClientProxy", serverSide = "cubex2.cs4.CommonProxy")
    public static CommonProxy proxy;

    @Mod.Instance(ID)
    public static CustomStuff4 INSTANCE;

    private List<CustomStuffPlugin> plugins;

    public static SimpleNetworkWrapper network = new SimpleNetworkWrapper("customstuff4");

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(EventHandler.class);

        initPlugins(event.getAsmData());

        File configDir = event.getModConfigurationDirectory();
        File modsDir = new File(configDir.getParent(), "mods");

        registerPackets();

        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
        RecipeSorter.register("customstuff4:shapedore", DamageableShapedOreRecipe.class, RecipeSorter.Category.SHAPED, "after:minecraft:shaped before:minecraft:shapeless");
        RecipeSorter.register("customstuff4:shapelessore", DamageableShapelessOreRecipe.class, RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
        ModLoader.initMods(modsDir);
    }

    private void initPlugins(ASMDataTable asmDataTable)
    {
        plugins = PluginHelper.getPluginInstances(asmDataTable);
        plugins.forEach(plugin -> plugin.registerContent(contentRegistry));
    }

    private void registerPackets()
    {
        network.registerMessage(PacketSyncContainerFluid.Handler.class, PacketSyncContainerFluid.class, 0, Side.CLIENT);
    }

    static
    {
        if (Bootstrap.isRegistered()) // Tests will fail otherwise
            FluidRegistry.enableUniversalBucket();
    }
}
