package cubex2.cs4.data;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializer;
import cubex2.cs4.api.Content;
import cubex2.cs4.api.ContentRegistry;
import cubex2.cs4.api.LoaderPredicate;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;

public class ContentRegistryImpl implements ContentRegistry, DeserializationRegistry, LoaderPredicateRegistry
{
    private final Map<String, Class<? extends Content>> types = Maps.newHashMap();
    private final Map<String, LoaderPredicate> predicates = Maps.newHashMap();
    private final List<Pair<Type, JsonDeserializer<?>>> deserializers = Lists.newArrayList();

    @Override
    public <T extends Content> void registerContentType(String typeName, Class<T> clazz)
    {
        checkArgument(!types.containsKey(typeName), "Duplicate typeName: %s", typeName);

        types.put(typeName, clazz);
    }

    @Nullable
    Class<? extends Content> getContentClass(String typeName)
    {
        return types.get(typeName);
    }

    @Override
    public <T> void registerDeserializer(Type type, JsonDeserializer<T> deserializer)
    {
        deserializers.add(Pair.of(type, deserializer));
    }

    @Override
    public List<Pair<Type, JsonDeserializer<?>>> getDeserializers()
    {
        return deserializers;
    }

    @Override
    public void registerLoaderPredicate(String name, LoaderPredicate predicate)
    {
        checkArgument(!predicates.containsKey(name), "Duplicate predicate name: %s", name);

        predicates.put(name, predicate);
    }

    @Nullable
    @Override
    public LoaderPredicate getPredicate(String name)
    {
        return predicates.get(name);
    }
}
