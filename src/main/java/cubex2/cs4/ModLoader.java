package cubex2.cs4;

import cubex2.cs4.api.Content;
import cubex2.cs4.asm.ModClassGenerator;
import cubex2.cs4.asm.ModInfo;
import cubex2.cs4.data.ContentLoader;
import cubex2.cs4.util.JsonHelper;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.ModContainer;

import java.io.File;
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
        ModContainer container = FMLCommonHandler.instance().findContainerFor(mod);
        File modDirectory = container.getSource();

        ContentLoader loader = new ContentLoader();
        loader.file = "main.json";
        loader.type = "contentLoader";

        List<? extends Content> contents = loader.loadContent(modDirectory, CustomStuff4.contentRegistry);
    }

    @SuppressWarnings("unused")
    public static void onInitMod(CS4Mod mod)
    {

    }

    @SuppressWarnings("unused")
    public static void onPostInitMod(CS4Mod mod)
    {

    }
}
