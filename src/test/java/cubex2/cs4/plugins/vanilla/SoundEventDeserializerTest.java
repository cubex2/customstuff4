package cubex2.cs4.plugins.vanilla;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import cubex2.cs4.TestUtil;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

public class SoundEventDeserializerTest
{
    private static Gson gson;

    @BeforeAll
    public static void setup()
    {
        gson = TestUtil.createGson();
    }

    @Test
    public void fromString()
    {
        Map<String, SoundEvent> map = gson.fromJson("{ \"sound\": \"item.armor.equip_gold\" }", new TypeToken<Map<String, SoundEvent>>() {}.getType());

        SoundEvent sound = map.get("sound");
        assertNotNull(sound);
        assertSame(SoundEvents.ITEM_ARMOR_EQUIP_GOLD, sound);
    }
}