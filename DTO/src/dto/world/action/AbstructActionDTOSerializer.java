package dto.world.action;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class AbstructActionDTOSerializer implements JsonSerializer<AbstructActionDTO> {
    /*
    * This method is called whenever Gson encounters a subclass of AbstructActionDTO
    * @param src the object that needs to be converted to JsonElement
    * @param typeOfSrc the actual type (fully genericized version) of the source object
    * @param context
    * @return a JsonElement corresponding to the specified object.
     */
    @Override
    public JsonElement serialize(AbstructActionDTO src, Type typeOfSrc, JsonSerializationContext context) {
        JsonElement jsonElement = context.serialize(src);
        jsonElement.getAsJsonObject().addProperty("type", src.getClass().getSimpleName());
        return jsonElement;
    }
}
