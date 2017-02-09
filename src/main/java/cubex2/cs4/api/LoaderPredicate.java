package cubex2.cs4.api;

import java.util.List;

public interface LoaderPredicate
{
    /**
     * Gets whether the content that this predicate was attached to should be loaded.
     *
     * @return True if content should be loaded, false if not.
     */
    boolean getResult(List<String> arguments);
}
