package dto.world.action;

import com.google.gson.*;

import java.lang.reflect.Type;

public class AbstructActionDTODeserializer implements JsonDeserializer<AbstructActionDTO> {
    @Override
    public AbstructActionDTO deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        // Parse the JSON and create an appropriate subclass of AbstructActionDTO
        // Example: Assuming there's a field named "type" in the JSON to determine the actual subclass to create
        String type = jsonObject.get("type").getAsString();
        switch (type) {
            case "IncreaseActionDTO":
                return context.deserialize(jsonObject, IncreaseActionDTO.class);
            case "DecreaseActionDTO":
                return context.deserialize(jsonObject, DecreaseActionDTO.class);
            case "CalculationActionDTO":
                return context.deserialize(jsonObject, CalculationActionDTO.class);
            case "ConditionActionDTO":
                return context.deserialize(jsonObject, ConditionActionDTO.class);
            case "SetActionDTO":
                return context.deserialize(jsonObject, SetActionDTO.class);
            case "KillActionDTO":
                return context.deserialize(jsonObject, KillActionDTO.class);
            case "ReplaceActionDTO":
                return context.deserialize(jsonObject, ReplaceActionDTO.class);
            case "ProximityActionDTO":
                return context.deserialize(jsonObject, ProximityActionDTO.class);
            default:
                    throw new JsonParseException("Unknown type: " + type);
        }
    }
}
