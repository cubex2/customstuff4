package cubex2.cs4;

import cubex2.cs4.asm.ModClassGenerator;
import cubex2.cs4.asm.ModInfo;
import cubex2.cs4.util.JsonHelper;

import java.io.File;

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
