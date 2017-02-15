package cubex2.cs4.plugins.vanilla;

import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import cubex2.cs4.api.*;
import cubex2.cs4.util.JsonHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.util.Map;

public class ShapedRecipe implements Content
{
    String[] shape;
    Map<Character, RecipeInput> items;
    WrappedItemStack result;
    boolean mirrored = true;

    private boolean initialized = false;

    @Override
    public void init(InitPhase phase, ContentHelper helper)
    {
        if (!initialized && isReady())
        {
            ShapedOreRecipe recipe = new ShapedOreRecipe(result.createItemStack(), getInputForRecipe()).setMirrored(mirrored);
            GameRegistry.addRecipe(recipe);

            initialized = true;
        }
    }

    private boolean isReady()
    {
        if (!result.isItemLoaded())
            return false;

        return items.values().stream().allMatch(input -> input.isOreClass() || (input.isItemStack() && input.getStack().isItemLoaded()));
    }

    private Object[] getInputForRecipe()
    {
        Object[] result = new Object[shape.length + items.size() * 2];

        System.arraycopy(shape, 0, result, 0, shape.length);

        int i = shape.length;
        for (Map.Entry<Character, RecipeInput> entry : items.entrySet())
        {
            RecipeInput input = entry.getValue();

            result[i] = entry.getKey();
            result[i + 1] = input.isOreClass() ? input.getOreClass() : input.getStack().createItemStack();

            i += 2;
        }

        return result;
    }

    public static final JsonDeserializer<ShapedRecipe> DESERIALIZER = (json, typeOfT, context) ->
    {
        JsonObject jsonObject = json.getAsJsonObject();
        ShapedRecipe recipe = new ShapedRecipe();

        if (jsonObject.has("shape"))
        {
            recipe.shape = JsonHelper.arrayFromElement(jsonObject.get("shape"));
        }

        if (jsonObject.has("items"))
        {
            recipe.items = context.deserialize(jsonObject.get("items"), new TypeToken<Map<Character, RecipeInput>>() {}.getType());
        } else
        {
            recipe.items = Maps.newHashMap();
        }

        if (jsonObject.has("result"))
        {
            recipe.result = context.deserialize(jsonObject.get("result"), WrappedItemStack.class);
        }

        if (jsonObject.has("mirrored"))
        {
            recipe.mirrored = jsonObject.get("mirrored").getAsBoolean();
        }

        return recipe;
    };
}
