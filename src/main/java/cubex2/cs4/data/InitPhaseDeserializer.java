package cubex2.cs4.data;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import cubex2.cs4.api.InitPhase;

import java.lang.reflect.Type;

public class InitPhaseDeserializer implements JsonDeserializer<InitPhase>
{
    @Override
    public InitPhase deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        if (json.isJsonPrimitive() && json.getAsJsonPrimitive().isString())
        {
            String phase = json.getAsString();

            for (InitPhase initPhase : InitPhase.values())
            {
                if (initPhase.name.equalsIgnoreCase(phase))
                    return initPhase;
            }

            throw new JsonParseException("Invalid phase: " + phase);
        } else
        {
            throw new JsonParseException("Invalid phase attribute");
        }
    }
}
