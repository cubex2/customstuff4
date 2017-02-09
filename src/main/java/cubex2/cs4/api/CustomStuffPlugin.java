package cubex2.cs4.api;

public interface CustomStuffPlugin
{
    /**
     * Register any content types. This is called during the preInit phase.
     */
    void registerContent(ContentRegistry registry);
}
