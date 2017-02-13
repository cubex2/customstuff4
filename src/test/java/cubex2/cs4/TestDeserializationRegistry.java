package cubex2.cs4;

import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializer;
import cubex2.cs4.api.InitPhase;
import cubex2.cs4.data.ContentLoader;
import cubex2.cs4.data.DeserializationRegistry;
import cubex2.cs4.data.InitPhaseDeserializer;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.reflect.Type;
import java.util.List;

public class TestDeserializationRegistry implements DeserializationRegistry
{
    @Override
    public List<Pair<Type, JsonDeserializer<?>>> getDeserializers()
    {
        return Lists.newArrayList(Pair.of(InitPhase.class, new InitPhaseDeserializer()),
                                  Pair.of(ContentLoader.class, ContentLoader.DESERIALIZER));
    }
}
