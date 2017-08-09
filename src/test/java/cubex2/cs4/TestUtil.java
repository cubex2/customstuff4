package cubex2.cs4;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import cubex2.cs4.plugins.vanilla.VanillaPlugin;
import net.minecraft.init.Bootstrap;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.reflect.Type;

public class TestUtil
{
    private static boolean registered = false;
    private static Gson gson;

    public static Gson createGson()
    {
        Bootstrap.register();

        if (gson == null)
        {
            GsonBuilder gsonBuilder = new GsonBuilder();

            if (!registered)
            {
                new VanillaPlugin().registerContent(CustomStuff4.contentRegistry);
                registered = true;
            }

            for (Pair<Type, JsonDeserializer<?>> pair : CustomStuff4.contentRegistry.getDeserializers())
            {
                gsonBuilder.registerTypeAdapter(pair.getLeft(), pair.getRight());
            }

            gson = gsonBuilder.create();
        }

        return gson;
    }
}
