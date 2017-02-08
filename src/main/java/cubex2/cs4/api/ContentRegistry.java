package cubex2.cs4.api;

public interface ContentRegistry
{
    /**
     * Register a new type of content. You may register the same class using different names.
     *
     * @param typeName The name of the content. This is the value that is being used for the 'type' attribute in json files.
     * @param clazz    The clazz to register. The class MUST be public and have a default constructor.
     */
    <T extends Content> void registerContentType(String typeName, Class<T> clazz);
}
