package me.nurio.minecraft.pinger.serializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;

import java.io.IOException;

public class MotdJsonDeserializer extends RawJsonDeserializer {

    @Override
    public String deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
        return super.deserialize(parser, context);
    }

}