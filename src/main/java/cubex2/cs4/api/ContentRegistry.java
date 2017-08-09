package cubex2.cs4.api;

import com.google.gson.JsonDeserializer;
import net.minecraftforge.fml.relauncher.Side;

import java.lang.reflect.Type;

public interface ContentRegistry
{
    /**
     * Register a new type of content. You may register the same class using different names.
     *
     * @param typeName The name of the content. This is the value that is being used for the 'type' attribute in json files.
     * @param clazz    The class to register. The class MUST be public and have a default constructor.
     */
    <T extends Content> void registerContentType(String typeName, Class<T> clazz);

    /**
     * Register a new type of content. You may register the same class using different names.
     *
     * @param typeName The name of the content. This is the value that is being used for the 'type' attribute in json files.
     * @param clazz    The class to register. The class MUST be public and have a default constructor.
     * @param side     The side on which the content should be loaded.
     */
    <T extends Content> void registerContentType(String typeName, Class<T> clazz, Side side);

    <T> void registerDeserializer(Type type, JsonDeserializer<T> deserializer);

    /**
     * Register a loader predicate. The predicate is used to determine whether specific content should be loaded.
     *
     * @param name The name of the predicate. This is the name used in the json file.
     */
    void registerLoaderPredicate(String name, LoaderPredicate predicate);

    /**
     * Register a new tile entity module type. You may register the same class using different names.
     *
     * @param typeName The name of the module. This is the value that is being used for the 'type' attribute in json files.
     * @param clazz    The class to register. If you don't register a custom deserializer for the module, the class must
     *                 have a default constructor
     */
    <T extends TileEntityModuleSupplier> void registerTileEntityModule(String typeName, Class<T> clazz);

    /**
     * Register a block tint function. You may register the same function using different names.
     *
     * @param name The name of the function. This is the value that is used in json files to reference the function.
     * @param tint The tint function to register.
     */
    void registerBlockTint(String name, BlockTint tint);

    /**
     * Register a named color. The name can be used in json files instead of specifying the color value there.
     *
     * @param name The name of the color.
     * @param rgba The rgba color value.
     */
    void registerColor(String name, int rgba);
}
