package cubex2.cs4.plugins.vanilla;

import cubex2.cs4.api.RecipeInput;
import cubex2.cs4.api.WrappedItemStack;

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
    public Object getInput()
    {
        if (isOreClass())
            return oreClass;
        else
            return stack.createItemStack();
    }
}
