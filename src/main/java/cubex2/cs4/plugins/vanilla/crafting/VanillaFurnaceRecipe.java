package cubex2.cs4.plugins.vanilla.crafting;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class VanillaFurnaceRecipe implements MachineRecipe
{
    private final ItemStack result;

    public VanillaFurnaceRecipe(ItemStack result) {this.result = result;}

    @Override
    public boolean matches(NonNullList<ItemStack> input, World world)
    {
        return compareItemStacks(FurnaceRecipes.instance().getSmeltingResult(input.get(0)), result);
    }

    private boolean compareItemStacks(ItemStack stack1, ItemStack stack2)
    {
        return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
    }

    @Override
    public NonNullList<ItemStack> getResult()
    {
        return NonNullList.withSize(1, result.copy());
    }

    @Override
    public int getInputStacks()
    {
        return 1;
    }
}
