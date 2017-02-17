package cubex2.cs4.plugins.vanilla;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;

import java.lang.reflect.Type;

class NBTTagCompoundDeserializer implements JsonDeserializer<NBTTagCompound>
{
    @Override
    public NBTTagCompound deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        try
        {
            return JsonToNBT.getTagFromJson(json.getAsString());
        } catch (NBTException e)
        {
            e.printStackTrace();
        }

        throw new JsonParseException("Failed to parse nbt");
    }
}
