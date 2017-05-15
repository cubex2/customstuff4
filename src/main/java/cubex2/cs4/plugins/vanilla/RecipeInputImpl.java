package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.api.RecipeInput;
import cubex2.cs4.api.WrappedItemStack;
import net.minecraft.item.ItemStack;

import static com.google.common.base.Preconditions.checkState;

public class RecipeInputImpl implements RecipeInput
{
    public String oreClass = null;
    public WrappedItemStack stack = null;

    public RecipeInputImpl()
    {
    }

    public RecipeInputImpl(String oreClass)
    {
        this.oreClass = oreClass;
    }

    public RecipeInputImpl(WrappedItemStack stack)
    {
        this.stack = stack;
    }

    @Override
    public boolean isOreClass()
    {
        return oreClass != null;
    }

    @Override
    public boolean isItemStack()
    {
        return oreClass == null;
    }

    @Override
    public String getOreClass() throws IllegalStateException
    {
        checkState(isOreClass(), "Input is not a ore class");

        return oreClass;
    }

    @Override
    public WrappedItemStack getStack() throws IllegalStateException
    {
        checkState(isItemStack(), "Input is not a stack");

        return stack;
    }

    public static RecipeInputImpl create(ItemStack stack)
    {
        return new RecipeInputImpl(new WrappedItemStackConstant(stack));
    }
}
