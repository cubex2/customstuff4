package cubex2.cs4.plugins.vanilla;

import com.google.gson.Gson;
import cubex2.cs4.TestUtil;
import cubex2.cs4.api.WrappedPotionEffect;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class PotionEffectDeserializerTests
{
    private static Gson gson;

    @BeforeClass
    public static void setup()
    {
        gson = TestUtil.createGson();
    }

    @Test
    public void test()
    {
        WrappedPotionEffect potionEffect = gson.fromJson("{ \"id\": \"minecraft:hunger\", \"duration\":1337, \"amplifier\":42, \"showParticles\":false }", WrappedPotionEffect.class);

        PotionEffect effect = potionEffect.getPotionEffect();
        assertNotNull(effect);
        assertSame(MobEffects.HUNGER, effect.getPotion());
        assertEquals(1337, effect.getDuration());
        assertEquals(42, effect.getAmplifier());
        assertFalse(effect.doesShowParticles());
    }

    @Test
    public void test_invalidId()
    {
        WrappedPotionEffect potionEffect = gson.fromJson("{ \"id\": \"minecraft:hunger123456\" }", WrappedPotionEffect.class);

        PotionEffect effect = potionEffect.getPotionEffect();
        assertNull(effect);
    }
}
