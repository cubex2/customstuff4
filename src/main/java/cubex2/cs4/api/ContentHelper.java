package cubex2.cs4.api;

import javax.annotation.Nullable;

public interface ContentHelper
{
    /**
     * Reads a json file.
     *
     * @param path The path to the file. The path is relative to the mod directory or jar/zip.
     * @return The json or null if the file doesn't exist.
     */
    @Nullable
    String readJson(String path);

    /**
     * Gets the content class associated with the given type.
     *
     * @return The class or null, if it hasn't been registered.
     */
    @Nullable
    Class<? extends Content> getContentClass(String typeName);

    /**
     * Gets the id of the active mod.
     */
    String getModId();
}
