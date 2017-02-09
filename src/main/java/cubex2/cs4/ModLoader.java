package cubex2.cs4;

import cubex2.cs4.api.Content;
import cubex2.cs4.api.ContentHelper;
import cubex2.cs4.api.InitPhase;
import cubex2.cs4.asm.ModClassGenerator;
import cubex2.cs4.asm.ModInfo;
import cubex2.cs4.data.ContentHelperImpl;
import cubex2.cs4.data.ContentLoader;
import cubex2.cs4.util.JsonHelper;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.ModContainer;

import java.io.File;
import java.util.Collections;
import java.util.List;

public class ModLoader
{
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
        initMainLoaders(mod, InitPhase.PRE_INIT);
    }

    @SuppressWarnings("unused")
    public static void onInitMod(CS4Mod mod)
    {
        initMainLoaders(mod, InitPhase.INIT);
    }

    @SuppressWarnings("unused")
    public static void onPostInitMod(CS4Mod mod)
    {
        initMainLoaders(mod, InitPhase.POST_INIT);
    }

    private static void initMainLoaders(CS4Mod mod, InitPhase phase)
    {
        ModContainer container = FMLCommonHandler.instance().findContainerFor(mod);
        File modDirectory = container.getSource();

        ContentHelper helper = new ContentHelperImpl(modDirectory, CustomStuff4.contentRegistry);
        List<ContentLoader> loaders = loadMainLoaders(helper);
        initContents(loaders, phase, helper);
    }

    private static List<ContentLoader> loadMainLoaders(ContentHelper helper)
    {
        String json = helper.readJson("main.json");
        if (json == null)
            return Collections.emptyList();
        else
            return ContentLoader.loadContent(json, ContentLoader.class, CustomStuff4.contentRegistry);
    }

    private static void initContents(List<? extends Content> contents, InitPhase phase, ContentHelper helper)
    {
        contents.forEach(content -> content.init(phase, helper));
    }
}
