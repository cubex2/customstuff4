package cubex2.cs4.plugins.vanilla;

import com.google.gson.*;

import java.lang.reflect.Type;

class IMCDeserializer implements JsonDeserializer<IMCBase>
{
    @Override
    public IMCBase deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        JsonObject object = json.getAsJsonObject();

        String type = object.get("type").getAsString();
        Class<? extends IMCBase> imcClass = getImplClass(type);

        return context.deserialize(json, imcClass);
    }

    private static Class<? extends IMCBase> getImplClass(String type) throws JsonParseException
    {
        switch (type)
        {
            case "string":
                return IMCString.class;
            case "nbt":
                return IMCNBT.class;
            case "resource":
                return IMCResourceLocation.class;
            case "itemstack":
                return IMCItemStack.class;
            case "function":
                return IMCFunction.class;
            default:
                throw new JsonParseException("Invalid imc type: " + type);
        }
    }
}
