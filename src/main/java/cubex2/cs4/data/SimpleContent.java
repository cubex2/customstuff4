package cubex2.cs4.data;

import cubex2.cs4.api.Content;
import cubex2.cs4.api.ContentHelper;
import cubex2.cs4.api.InitPhase;

public abstract class SimpleContent implements Content
{
    private boolean isInitialized = false;

    @Override
    public final void init(InitPhase phase, ContentHelper helper)
    {
        if (!isInitialized && isReady())
        {
            doInit(phase, helper);

            isInitialized = true;
        }
    }

    /**
     * This is called for the first phase for which isReady returns true.
     */
    protected abstract void doInit(InitPhase phase, ContentHelper helper);

    protected abstract boolean isReady();
}
