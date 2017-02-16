package cubex2.cs4;

import com.google.common.collect.Maps;
import cubex2.cs4.api.ContentHelper;
import cubex2.cs4.api.InitPhase;
import cubex2.cs4.asm.ModClassGenerator;
import cubex2.cs4.asm.ModInfo;
import cubex2.cs4.data.ContentHelperFactory;
import cubex2.cs4.data.ContentHelperImpl;
import cubex2.cs4.data.ContentLoader;
import cubex2.cs4.data.DeserializationRegistry;
import cubex2.cs4.util.JsonHelper;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.ModContainer;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ModLoader
{
    private static final Map<CS4Mod, ModData> mainContentLoaders = Maps.newHashMap();

    static void initMods(File modsDir)
    {
        File[] modFolders = modsDir.listFiles(File::isDirectory);
        if (modFolders != null)
        {
            for (File folder : modFolders)
            {
                File infoJsonFile = new File(folder, "cs4mod.json");
                if (infoJsonFile.exists() && infoJsonFile.isFile())
                {
                    createMod(infoJsonFile);
                }
            }
        }
    }

    private static void createMod(File infoJsonFile)
    {
        ModInfo info = JsonHelper.deserialize(infoJsonFile, ModInfo.class);
        if (info != null)
        {
            ModClassGenerator.createModClass(infoJsonFile.getParentFile(), info);
        }
    }

    @SuppressWarnings("unused")
    public static void onPreInitMod(CS4Mod mod)
    {
        doPreInitMod(mod, ModLoader::createHelper, CustomStuff4.contentRegistry);
    }

    static void doPreInitMod(CS4Mod mod, ContentHelperFactory helperFactory, DeserializationRegistry deserializationRegistry)
    {
        deserializeMainLoaders(mod, helperFactory, deserializationRegistry);

        initContents(mod, InitPhase.PRE_INIT);
    }

    private static ContentHelper createHelper(CS4Mod mod)
    {
        ModContainer container = FMLCommonHandler.instance().findContainerFor(mod);
        File modDirectory = container.getSource();

        return new ContentHelperImpl(modDirectory);
    }

    @SuppressWarnings("unused")
    public static void onInitMod(CS4Mod mod)
    {
        initContents(mod, InitPhase.INIT);
    }

    @SuppressWarnings("unused")
    public static void onPostInitMod(CS4Mod mod)
    {
        initContents(mod, InitPhase.POST_INIT);
    }

    private static void deserializeMainLoaders(CS4Mod mod, ContentHelperFactory helperFactory, DeserializationRegistry deserializationRegistry)
    {
        ContentHelper helper = helperFactory.createHelper(mod);
        List<ContentLoader> loaders = loadMainLoaders(helper, deserializationRegistry);
        loaders.forEach(loader -> loader.deserializeContent(helper));

        mainContentLoaders.put(mod, new ModData(helper, loaders));
    }

    private static List<ContentLoader> loadMainLoaders(ContentHelper helper, DeserializationRegistry deserializationRegistry)
    {
        String json = helper.readJson("main.json");
        if (json == null)
            return Collections.emptyList();
        else
            return ContentLoader.loadContent(json, ContentLoader.class, deserializationRegistry);
    }

    private static void initContents(CS4Mod mod, InitPhase phase)
    {
        ModData modData = mainContentLoaders.get(mod);
        if (modData != null)
        {
            modData.mainLoaders.forEach(content -> content.init(phase, modData.helper));
        }
    }

    private static class ModData
    {
        private final ContentHelper helper;
        private final List<ContentLoader> mainLoaders;

        private ModData(ContentHelper helper, List<ContentLoader> mainLoaders)
        {
            this.helper = helper;
            this.mainLoaders = mainLoaders;
        }
    }
}
