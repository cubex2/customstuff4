package cubex2.cs4.api;

import net.minecraft.entity.Entity;

import javax.annotation.Nullable;

public interface EntitySelector<T extends Entity>
{
    /**
     * Get the class that an entity selected by this selector must be a subclass of.
     *
     * @return The class or null to allow all entities.
     */
    @Nullable
    Class<? extends T> getEntityClass();

    /**
     * Checks whether the given entity is being selected.
     */
    boolean isValidEntity(T entity);

    EntitySelector<Entity> EVERYTHING = new EntitySelector<Entity>()
    {
        @Nullable
        @Override
        public Class<? extends Entity> getEntityClass()
        {
            return null;
        }

        @Override
        public boolean isValidEntity(Entity entity)
        {
            return true;
        }
    };

    EntitySelector<Entity> NOTHING = new EntitySelector<Entity>()
    {
        @Nullable
        @Override
        public Class<? extends Entity> getEntityClass()
        {
            return null;
        }

        @Override
        public boolean isValidEntity(Entity entity)
        {
            return false;
        }
    };
}
