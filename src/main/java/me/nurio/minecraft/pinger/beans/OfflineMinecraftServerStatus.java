package me.nurio.minecraft.pinger.beans;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.nurio.minecraft.pinger.MinecraftServerPinger;

/**
 * This class represents an offline minecraft server.
 *
 * @see MinecraftServerPinger#ping(String)
 */
@AllArgsConstructor
public class OfflineMinecraftServerStatus implements MinecraftServerStatus {

    @Getter private Throwable exception;

}