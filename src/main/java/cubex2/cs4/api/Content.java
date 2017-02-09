package cubex2.cs4.api;

public interface Content
{
    /**
     * Initialize the content. This is being called on all phases.
     */
    void init(InitPhase phase, ContentHelper helper);
}
