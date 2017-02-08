package cubex2.cs4.api;

public interface ContentRegistry
{
    /**
     * Register a new type of content.
     *
     * @param typeName The name of the content. This is the value that is being used for the 'type' attribute in json files.
     * @param clazz    The clazz to register. The class MUST have a default constructor.
     */
    <T extends Content> void registerContentType(String typeName, Class<T> clazz);
}
