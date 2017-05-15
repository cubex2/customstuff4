package cubex2.cs4.plugins.vanilla.gui;

public interface ProgressBarSource
{
    /**
     * Get the progress for the bar with the given name. The returned value should be in [0.0, 1.0]. If the name is not
     * supported by this source, return 0.0.
     */
    float getProgress(String name);
}
