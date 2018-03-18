package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.api.EntitySelector;
import net.minecraft.entity.Entity;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public class EntitySelectorSimple<T extends Entity> implements EntitySelector<T>
{
    private final Class<T> clazz;
    private final Predicate<T> predicate;

    public EntitySelectorSimple(Class<T> clazz, Predicate<T> predicate)
    {
        this.clazz = clazz;
        this.predicate = predicate;
    }

    @Nullable
    @Override
    public Class<? extends T> getEntityClass()
    {
        return clazz;
    }

    @Override
    public boolean isValidEntity(T entity)
    {
        return predicate.test(entity);
    }
}
