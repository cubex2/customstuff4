package cubex2.cs4;

import cubex2.cs4.api.CustomStuffPlugin;
import cubex2.cs4.data.ContentRegistryImpl;
import cubex2.cs4.plugins.vanilla.EventHandler;
import cubex2.cs4.plugins.vanilla.GuiHandler;
import cubex2.cs4.plugins.vanilla.gui.CapabilityItemHandlerSupplier;
import cubex2.cs4.plugins.vanilla.network.PacketSyncContainerFluid;
import cubex2.cs4.script.runtime.ScriptHandler;
import cubex2.cs4.script.util.ReloadCommand;
import cubex2.cs4.util.PluginHelper;
import frontrider.repack.delight.nashornsandbox.exceptions.NotScriptedException;
import net.minecraft.init.Bootstrap;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

import javax.script.ScriptException;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Mod(modid = CustomStuff4.ID, name = CustomStuff4.NAME, version = CustomStuff4.VERSION,
        acceptedMinecraftVersions = CustomStuff4.MC_VERSION)
public class CustomStuff4 {
    public static final String ID = "customstuff4";
    public static final String NAME = "Custom Stuff 4";
    public static final String VERSION = "0.5.1";
    public static final String MC_VERSION = "[1.12,)";

    public static final ContentRegistryImpl contentRegistry = new ContentRegistryImpl();

    @SidedProxy(clientSide = "cubex2.cs4.ClientProxy", serverSide = "cubex2.cs4.CommonProxy")
    public static CommonProxy proxy;

    @Mod.Instance(ID)
    public static CustomStuff4 INSTANCE;

    public static ScriptHandler scriptHandler;
    public static  File modsDir;
    private List<CustomStuffPlugin> plugins;

    public static SimpleNetworkWrapper network = new SimpleNetworkWrapper("customstuff4");

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) throws ScriptException, IOException, NotScriptedException {
        CapabilityItemHandlerSupplier.register();
        MinecraftForge.EVENT_BUS.register(EventHandler.class);

        initPlugins(event.getAsmData());

        File configDir = event.getModConfigurationDirectory();
        modsDir = new File(configDir.getParent(), "mods");

        registerPackets();

        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());

        ModLoader.initMods(modsDir);
        System.out.println("checking scripts");
        if (CustomStuffConfiguration.ScriptEnabled) {
            System.out.println("Scripts are enabled, creating environment");
            System.out.println("Ignore this error, already handled");
            scriptHandler = new ScriptHandler();
            System.out.println("environment created, loading scripts");
            try {
                scriptHandler.load(modsDir);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            System.out.println("scripts loaded, everything ready to go!");
        }else {
            System.out.println("Scripts are disabled, moving on");
        }


    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        sendIMCs();
    }

    private void sendIMCs() {
        FMLInterModComms.sendMessage("waila", "register", "cubex2.cs4.compat.waila.CompatWaila.callbackRegister");
    }

    private void initPlugins(ASMDataTable asmDataTable) {
        plugins = PluginHelper.getPluginInstances(asmDataTable);
        plugins.forEach(plugin -> plugin.registerContent(contentRegistry));
    }

    private void registerPackets() {
        network.registerMessage(PacketSyncContainerFluid.Handler.class, PacketSyncContainerFluid.class, 0, Side.CLIENT);
    }

    static {
        if (Bootstrap.isRegistered()) // Tests will fail otherwise
            FluidRegistry.enableUniversalBucket();
    }

    public static Optional<CommonProxy> getProxy() {
        return Optional.ofNullable(proxy);
    }

    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent event)
    {
        event.registerServerCommand(new ReloadCommand());
    }

}
