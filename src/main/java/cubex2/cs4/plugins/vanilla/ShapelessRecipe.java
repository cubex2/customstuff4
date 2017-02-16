package cubex2.cs4.plugins.vanilla;

import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import cubex2.cs4.api.*;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.List;

class ShapelessRecipe implements Content
{
    List<RecipeInput> items = Lists.newArrayList();
    WrappedItemStack result;

    private boolean initialized = false;

    @Override
    public void init(InitPhase phase, ContentHelper helper)
    {
        if (!initialized && isReady())
        {
            ShapelessOreRecipe recipe = new ShapelessOreRecipe(result.createItemStack(), getInputForRecipe());
            GameRegistry.addRecipe(recipe);

            initialized = true;
        }
    }

    private boolean isReady()
    {
        if (!result.isItemLoaded())
            return false;

        return items.stream().allMatch(input -> input.isOreClass() || (input.isItemStack() && input.getStack().isItemLoaded()));
    }

    Object[] getInputForRecipe()
    {
        return items.stream()
                    .map(input -> input.isOreClass() ? input.getOreClass() : input.getStack().createItemStack())
                    .toArray();
    }

    public static final JsonDeserializer<ShapelessRecipe> DESERIALIZER = (json, typeOfT, context) ->
    {
        JsonObject jsonObject = json.getAsJsonObject();
        ShapelessRecipe recipe = new ShapelessRecipe();

        if (jsonObject.has("items"))
        {
            recipe.items = context.deserialize(jsonObject.get("items"), new TypeToken<List<RecipeInput>>() {}.getType());
        }

        if (jsonObject.has("result"))
        {
            recipe.result = context.deserialize(jsonObject.get("result"), WrappedItemStack.class);
        }

        return recipe;
    };
}
