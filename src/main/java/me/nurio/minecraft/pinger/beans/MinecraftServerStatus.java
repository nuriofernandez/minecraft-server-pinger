package me.nurio.minecraft.pinger.beans;

import me.nurio.minecraft.pinger.exceptions.OfflineMinecraftServerFetchingException;

public interface MinecraftServerStatus {

    default long getPing() {
        throw new OfflineMinecraftServerFetchingException("You can't fetch data from an offline server.");
    }

    default Version getVersion() {
        throw new OfflineMinecraftServerFetchingException("You can't fetch data from an offline server.");
    }

    default String getMotd() {
        throw new OfflineMinecraftServerFetchingException("You can't fetch data from an offline server.");
    }

    default String getFavicon() {
        throw new OfflineMinecraftServerFetchingException("You can't fetch data from an offline server.");
    }

    default Players getPlayers() {
        throw new OfflineMinecraftServerFetchingException("You can't fetch data from an offline server.");
    }

    default boolean isOnline() {
        return this instanceof OnlineMinecraftServerStatus;
    }

    default boolean isOffline() {
        return !isOnline();
    }

}