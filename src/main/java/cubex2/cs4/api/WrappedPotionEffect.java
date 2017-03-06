package cubex2.cs4.api;

import net.minecraft.potion.PotionEffect;

import javax.annotation.Nullable;

public interface WrappedPotionEffect
{
    @Nullable
    PotionEffect getPotionEffect();

    static WrappedPotionEffect of(@Nullable PotionEffect effect)
    {
        return () -> effect;
    }
}
