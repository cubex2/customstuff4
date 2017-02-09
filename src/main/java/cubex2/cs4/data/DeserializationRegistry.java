package cubex2.cs4.data;

import com.google.gson.JsonDeserializer;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.reflect.Type;
import java.util.List;

public interface DeserializationRegistry
{
    List<Pair<Type, JsonDeserializer<?>>> getDeserializers();
}
