package br.com.alura.ecommerce;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class MessageAdapter implements JsonSerializer<Message> {
    @Override
    public JsonElement serialize(Message message, Type type, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        obj.addProperty("type", message.getPayload().getClass().getName());// passa o tipo da classe do payload
        obj.add("payload", context.serialize(message.getPayload())); // passa a classe do payload
        obj.add("correlationId", context.serialize(message.getId())); // passa o correlation id
        return obj;
    }
}
