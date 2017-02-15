package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.api.RecipeInput;
import cubex2.cs4.api.WrappedItemStack;

import static com.google.common.base.Preconditions.checkState;

class RecipeInputImpl implements RecipeInput
{
    public String oreClass = null;
    public WrappedItemStack stack = null;

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
}
