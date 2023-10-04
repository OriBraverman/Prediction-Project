package dto.world.action;

import com.google.gson.*;

import java.lang.reflect.Type;

public class AbstructActionDTODeserializer implements JsonDeserializer<AbstructActionDTO> {
    @Override
    public AbstructActionDTO deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        // Parse the JSON and create an appropriate subclass of AbstructActionDTO
        // Example: Assuming there's a field named "type" in the JSON to determine the actual subclass to create
        String type = jsonObject.get("type").getAsString().toLowerCase();
        switch (type) {
            case "increase":
                return context.deserialize(jsonObject, IncreaseActionDTO.class);
            case "decrease":
                return context.deserialize(jsonObject, DecreaseActionDTO.class);
            case "calculation":
                return context.deserialize(jsonObject, CalculationActionDTO.class);
            case "condition":
                return context.deserialize(jsonObject, ConditionActionDTO.class);
            case "set":
                return context.deserialize(jsonObject, SetActionDTO.class);
            case "kill":
                return context.deserialize(jsonObject, KillActionDTO.class);
            case "replace":
                return context.deserialize(jsonObject, ReplaceActionDTO.class);
            case "proximity":
                return context.deserialize(jsonObject, ProximityActionDTO.class);
            default:
                    throw new JsonParseException("Unknown type: " + type);
        }
    }
}
