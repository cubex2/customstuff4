package cubex2.cs4.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cubex2.cs4.api.InitPhase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertSame;

@DisplayName("Init phase deserializer")
public class InitPhaseDeserializerTest
{
    @Test
    public void testDeserializer()
    {
        Gson gson = new GsonBuilder().registerTypeAdapter(InitPhase.class, new InitPhaseDeserializer()).create();

        assertSame(InitPhase.PRE_INIT, gson.fromJson("preInit", InitPhase.class));
        assertSame(InitPhase.INIT, gson.fromJson("INIT", InitPhase.class));
        assertSame(InitPhase.POST_INIT, gson.fromJson("postinit", InitPhase.class));
    }
}
