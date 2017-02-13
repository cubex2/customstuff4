package cubex2.cs4.api;

public interface Content
{
    /**
     * Initialize the content. This is called on the same instance for each phase.
     */
    void init(InitPhase phase, ContentHelper helper);
}
