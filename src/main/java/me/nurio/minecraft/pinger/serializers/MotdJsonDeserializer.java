package me.nurio.minecraft.pinger.serializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class MotdJsonDeserializer extends RawJsonDeserializer {

    private JsonNode node;
    private ObjectMapper mapper;

    @Override
    public String deserialize(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException {
        mapper = (ObjectMapper) parser.getCodec();

        String json = super.deserialize(parser, context);
        node = mapper.readTree(json);

        String motd = (json.contains("\"text\":")) ? deserializeExtended() : deserializeLegacy();
        return removeIllegalCharacters(motd);
    }

    public String deserializeLegacy() {
        String motd = node.toString();
        return motd.substring(1, motd.length() - 1);
    }

    public String deserializeExtended() {
        return String.join("", node.findValuesAsText("text"));
    }

    private String removeIllegalCharacters(String text) {
        return text.replaceAll("(((Â?)([Â,]?)(Ã?))?[§](\\w))", "")
            .replaceAll("\n", " ")
            .trim();
    }

}