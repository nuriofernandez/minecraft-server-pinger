package me.nurio.minecraft.pinger.beans;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import me.nurio.minecraft.pinger.serializers.MotdJsonDeserializer;

@ToString
public class StatusResponse {

    @JsonDeserialize(using = MotdJsonDeserializer.class, as = String.class)
    @Getter private String description;
    @Getter private Players players;
    @Getter private Version version;
    @Getter private String favicon;

    @Getter @Setter private long time;

}