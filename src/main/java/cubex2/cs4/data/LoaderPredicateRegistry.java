package cubex2.cs4.data;

import cubex2.cs4.api.LoaderPredicate;

import javax.annotation.Nullable;

public interface LoaderPredicateRegistry
{
    @Nullable
    LoaderPredicate getPredicate(String name);
}
