package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.api.EntitySelector;

import javax.annotation.Nullable;

public interface EntitySelectorRegistry
{
    @Nullable
    EntitySelector<?> getEntitySelector(String name);
}
