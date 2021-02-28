package me.nurio.minecraft.pinger.serializers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class MotdJsonDeserializerTest {

    private static final String MOTD_1_7 = "{\"description\":\"Lobby Â§cRED color Ã§7for future\"}";
    private static final String MOTD_1_10 = "{\"description\":{\"extra\":[{\"color\":\"red\",\"bold\":true,\"text\":\"Koru Network \"},{\"color\":\"gray\",\"text\":\"| \"},{\"color\":\"green\",\"text\":\"(Beta Phase)\\n\"},{\"color\":\"gray\",\"text\":\"Check out our brand new website at \"},{\"color\":\"gray\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"http://www.koru.rip\\n\"},\"text\":\"www.koru.rip\\n\"}],\"text\":\"\"}}";

    private ObjectMapper mapper = new ObjectMapper() {
        {
            disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        }
    };

    @Test
    public void deserializes_legacy_1_7_successful() throws IOException {
        MotdTestBean motdTestBean = mapper.readValue(MOTD_1_7, MotdTestBean.class);
        assertEquals("Lobby RED color for future", motdTestBean.getDescription());
    }

    @Test
    public void deserializes_1_10_successful() throws IOException {
        MotdTestBean motdTestBean = mapper.readValue(MOTD_1_10, MotdTestBean.class);
        assertEquals("Koru Network | (Beta Phase) Check out our brand new website at www.koru.rip", motdTestBean.getDescription());
    }

}

@AllArgsConstructor
@NoArgsConstructor
class MotdTestBean {
    @JsonDeserialize(using = MotdJsonDeserializer.class, as = String.class)
    @Getter private String description;
}