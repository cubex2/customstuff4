package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.api.WrappedPotionEffect;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

class WrappedPotionEffectImpl implements WrappedPotionEffect
{
    ResourceLocation id;
    int duration = 60;
    int amplifier = 1;
    boolean showParticles = true;

    @Override
    public PotionEffect getPotionEffect()
    {
        Potion potion = Potion.REGISTRY.getObject(id);
        if (potion == null)
        {
            return null;
        } else
        {
            return new PotionEffect(potion, duration, amplifier, false, showParticles);
        }
    }
}
