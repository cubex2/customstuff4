package cubex2.cs4.plugins.vanilla.crafting;

import cubex2.cs4.api.RecipeInput;
import net.minecraft.item.ItemStack;

import java.util.List;

public interface MachineFuel
{
    /**
     * Return the burn time for the given items. If the list contains all needed items but additional items as well,
     * this should still return 0.
     */
    int getBurnTime();

    boolean matches(List<ItemStack> items);

    List<RecipeInput> getFuelInput();

    MachineFuel EMPTY = new EmptyFuel();
}
