package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.api.EntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class EntitySelectorFromMobName implements EntitySelector<Entity>
{
    private final ResourceLocation mobName;
    private Class<? extends Entity> entityClass;

    public EntitySelectorFromMobName(ResourceLocation mobName)
    {
        this.mobName = mobName;
    }

    @Nullable
    @Override
    public Class<? extends Entity> getEntityClass()
    {
        if (entityClass == null)
        {
            entityClass = EntityList.getClass(mobName);
        }

        return entityClass;
    }

    @Override
    public boolean isValidEntity(Entity entity)
    {
        if (entityClass == null)
        {
            entityClass = EntityList.getClass(mobName);
        }

        return entityClass != null;
    }
}
