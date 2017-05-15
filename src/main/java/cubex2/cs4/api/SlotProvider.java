package cubex2.cs4.api;

import net.minecraft.inventory.Slot;

import java.util.Optional;

public interface SlotProvider
{
    Optional<Slot> createSlot(int index, int x, int y);
}
