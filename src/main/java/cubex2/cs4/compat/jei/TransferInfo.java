package cubex2.cs4.compat.jei;

import com.google.common.collect.Lists;
import cubex2.cs4.plugins.vanilla.gui.ContainerGui;
import mezz.jei.api.recipe.transfer.IRecipeTransferInfo;
import net.minecraft.inventory.Slot;

import java.util.ArrayList;
import java.util.List;

public class TransferInfo implements IRecipeTransferInfo<ContainerGui>
{
    private final String uid;
    private final String moduleName;
    private final int inputSlots;

    public TransferInfo(String uid, String moduleName, int inputSlots)
    {
        this.uid = uid;
        this.moduleName = moduleName;
        this.inputSlots = inputSlots;
    }

    @Override
    public Class<ContainerGui> getContainerClass()
    {
        return ContainerGui.class;
    }

    @Override
    public String getRecipeCategoryUid()
    {
        return uid;
    }

    @Override
    public boolean canHandle(ContainerGui container)
    {
        return true;
    }

    @Override
    public List<Slot> getRecipeSlots(ContainerGui container)
    {
        ArrayList<Slot> slots = Lists.newArrayList(container.getSlotsForSource(moduleName));
        slots.removeIf(slot -> slot.getSlotIndex() >= inputSlots);
        return slots;
    }

    @Override
    public List<Slot> getInventorySlots(ContainerGui container)
    {

        return Lists.newArrayList(container.getSlotsForSource("player"));
    }
}
