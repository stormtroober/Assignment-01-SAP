package sap.ass01.hexagonal.infrastructure.database.diskdb;

import com.google.gson.*;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;

public class OptionalTypeAdapter<T> implements JsonSerializer<Optional<T>>, JsonDeserializer<Optional<T>> {

    @Override
    public JsonElement serialize(Optional<T> src, Type typeOfSrc, JsonSerializationContext context) {
        return src.isPresent() ? context.serialize(src.get()) : JsonNull.INSTANCE;
    }

    @Override
    public Optional<T> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return json == null || json.isJsonNull() ? Optional.empty() : Optional.of(context.deserialize(json, ((ParameterizedType) typeOfT).getActualTypeArguments()[0]));
    }
}