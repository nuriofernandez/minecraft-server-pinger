package me.nurio.minecraft.pinger.exceptions;

import lombok.Getter;

@Getter
public class OfflineMinecraftServerFetchingException extends RuntimeException {

    private String message;

    public OfflineMinecraftServerFetchingException(String message) {
        super(message);
    }

}