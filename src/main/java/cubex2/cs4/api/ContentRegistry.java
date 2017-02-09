package cubex2.cs4.api;

import com.google.gson.JsonDeserializer;

import javax.annotation.Nullable;
import java.lang.reflect.Type;

public interface ContentRegistry
{
    /**
     * Register a new type of content. You may register the same class using different names.
     *
     * @param typeName The name of the content. This is the value that is being used for the 'type' attribute in json files.
     * @param clazz    The clazz to register. The class MUST be public and have a default constructor.
     */
    <T extends Content> void registerContentType(String typeName, Class<T> clazz);

    @Nullable
    Class<? extends Content> getContentClass(String typeName);

    <T> void registerDeserializer(Type type, JsonDeserializer<T> deserializer);

    /**
     * Register a loader predicate. The predicate is used to determine whether specific content should be loaded.
     *
     * @param name The name of the predicate. This is the name used in the json file.
     */
    void registerLoaderPredicate(String name, LoaderPredicate predicate);
}
