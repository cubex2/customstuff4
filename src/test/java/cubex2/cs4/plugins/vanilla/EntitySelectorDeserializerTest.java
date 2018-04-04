package cubex2.cs4.plugins.vanilla;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import cubex2.cs4.TestUtil;
import cubex2.cs4.api.EntitySelector;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EntitySelectorDeserializerTest
{
    private static Gson gson;

    @BeforeAll
    public static void setup()
    {
        gson = TestUtil.createGson();
    }

    @Test
    public void everything()
    {
        Map<String, EntitySelector> map = gson.fromJson("{ \"selector\": \"everything\" }", new TypeToken<Map<String, EntitySelector>>() {}.getType());

        EntitySelector selector = map.get("selector");

        assertSame(EntitySelector.EVERYTHING, selector);
    }

    @Test
    public void nothing()
    {
        Map<String, EntitySelector> map = gson.fromJson("{ \"selector\": \"nothing\" }", new TypeToken<Map<String, EntitySelector>>() {}.getType());

        EntitySelector selector = map.get("selector");

        assertSame(EntitySelector.NOTHING, selector);
    }
    @Test
    public void mobName()
    {
        Map<String, EntitySelector> map = gson.fromJson("{ \"selector\": \"sheep\" }", new TypeToken<Map<String, EntitySelector>>() {}.getType());

        EntitySelector selector = map.get("selector");

        assertTrue(selector instanceof EntitySelectorFromMobName);
    }
}