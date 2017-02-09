package cubex2.cs4.api;

public interface Content
{
    /**
     * Initialize the content. This is called on all phases if no 'initPhase' attribute
     * is defined in the loader json.
     */
    void init(InitPhase phase, ContentHelper helper);
}
