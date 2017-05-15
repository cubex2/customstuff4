package cubex2.cs4.plugins.vanilla.gui;

import com.google.common.collect.Lists;
import com.google.gson.*;
import cubex2.cs4.api.WrappedItemStack;
import cubex2.cs4.plugins.vanilla.crafting.MachineManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;

import java.lang.reflect.Type;
import java.util.List;

public class ItemFilterDeserializer implements JsonDeserializer<ItemFilter>
{
    @Override
    public ItemFilter deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        if (json.isJsonPrimitive() && json.getAsJsonPrimitive().isString())
        {
            return fromString(json.getAsString(), context);
        }

        if (json.isJsonArray())
        {
            return combinedFilterOr(json.getAsJsonArray(), context);
        }

        if (json.isJsonObject())
        {
            return new StackFilter(context.deserialize(json, WrappedItemStack.class));
        }

        throw new JsonParseException("Invalid item filter: " + json.toString());
    }

    private ItemFilter fromString(String input, JsonDeserializationContext context)
    {
        if (input.startsWith("machineInput:"))
        {
            ResourceLocation recipeList = context.deserialize(new JsonPrimitive(input.substring("machineInput:".length())), ResourceLocation.class);

            return stack -> MachineManager.isPartOfRecipe(recipeList, stack);
        }

        if (input.startsWith("machineFuel:"))
        {
            ResourceLocation recipeList = context.deserialize(new JsonPrimitive(input.substring("machineFuel:".length())), ResourceLocation.class);

            return stack -> MachineManager.isPartOfFuel(recipeList, stack);
        }

        if (input.startsWith("ore:"))
        {
            String oreName = input.substring("ore:".length());

            return stack -> OreDictionary.containsMatch(false, OreDictionary.getOres(oreName), stack);
        }

        WrappedItemStack wrappedStack = context.deserialize(new JsonPrimitive(input), WrappedItemStack.class);
        return new StackFilter(wrappedStack);
    }

    private ItemFilter combinedFilterOr(JsonArray array, JsonDeserializationContext context)
    {
        List<ItemFilter> filters = Lists.newArrayList();

        for (int i = 0; i < array.size(); i++)
        {
            ItemFilter filter = context.deserialize(array.get(i), ItemFilter.class);
            filters.add(filter);
        }

        return stack -> filters.stream().anyMatch(f -> f.accepts(stack));
    }

    static class StackFilter implements ItemFilter
    {
        private final WrappedItemStack wrappedStack;
        private ItemStack itemStack = ItemStack.EMPTY;

        StackFilter(WrappedItemStack wrappedStack) {this.wrappedStack = wrappedStack;}

        @Override
        public boolean accepts(ItemStack stack)
        {
            if (itemStack == ItemStack.EMPTY)
            {
                itemStack = wrappedStack.getItemStack();
            }
            return !itemStack.isEmpty() && OreDictionary.itemMatches(itemStack, stack, false);
        }
    }
}
