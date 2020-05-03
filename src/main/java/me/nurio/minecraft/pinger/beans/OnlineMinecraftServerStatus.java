package me.nurio.minecraft.pinger.beans;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import me.nurio.minecraft.pinger.MinecraftServerConnection;
import me.nurio.minecraft.pinger.serializers.MotdJsonDeserializer;

/**
 * This class represents an online minecraft server.
 *
 * @see MinecraftServerConnection#fetchData()
 */
@ToString
@NoArgsConstructor
public class OnlineMinecraftServerStatus implements MinecraftServerStatus {

    @JsonDeserialize(using = MotdJsonDeserializer.class, as = String.class)
    @JsonProperty("description")
    @Getter private String motd;
    @Getter private Players players;
    @Getter private Version version;
    @Getter private String favicon;

    @Getter @Setter private long ping;

}