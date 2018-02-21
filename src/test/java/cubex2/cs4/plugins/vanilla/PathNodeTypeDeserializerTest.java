package cubex2.cs4.plugins.vanilla;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import cubex2.cs4.TestUtil;
import net.minecraft.pathfinding.PathNodeType;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertSame;

public class PathNodeTypeDeserializerTest
{
    private static Gson gson;

    @BeforeClass
    public static void setup()
    {
        gson = TestUtil.createGson();
    }

    @Test
    public void test_existing()
    {
        Map<String, PathNodeType> map = gson.fromJson("{ \"pathNodeType\":\"blocked\" }", new TypeToken<Map<String, PathNodeType>>() {}.getType());

        PathNodeType nodeType = map.get("pathNodeType");

        assertSame(PathNodeType.BLOCKED, nodeType);
    }

    @Test(expected = JsonParseException.class)
    public void test_non_existing()
    {
        Map<String, PathNodeType> map = gson.fromJson("{ \"pathNodeType\":\"unknownnodetype\" }", new TypeToken<Map<String, PathNodeType>>() {}.getType());
    }
}