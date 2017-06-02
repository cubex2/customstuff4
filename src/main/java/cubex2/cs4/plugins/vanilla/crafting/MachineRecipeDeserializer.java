package cubex2.cs4.plugins.vanilla.crafting;

import com.google.gson.*;
import cubex2.cs4.plugins.jei.JEICompatRegistry;
import cubex2.cs4.plugins.vanilla.MachineRecipeImpl;
import net.minecraft.util.ResourceLocation;
import org.objectweb.asm.Opcodes;

import java.lang.reflect.Type;

public class MachineRecipeDeserializer implements JsonDeserializer<MachineRecipeImpl>, Opcodes
{
    @Override
    public MachineRecipeImpl deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        JsonObject object = json.getAsJsonObject();

        ResourceLocation recipeList = context.deserialize(object.get("recipeList"), ResourceLocation.class);

        return context.deserialize(json, JEICompatRegistry.getMachineRecipeClass(recipeList));
    }
}
