package me.nurio.minecraft.pinger;

import me.nurio.minecraft.pinger.beans.MinecraftServerStatus;
import me.nurio.minecraft.pinger.beans.OnlineMinecraftServerStatus;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MinecraftServerPingerTest {

    @Test
    public void ping_shouldReturnOffline_whenServerWasOffline() {
        MinecraftServerStatus server = MinecraftServerPinger.ping("localhost:2442");
        assertFalse(server.isOnline());
    }

    @Test
    public void ping_shouldReturnOnline_whenServerWasOnline() {
        MinecraftServerStatus server = MinecraftServerPinger.ping("mc.hypixel.net");
        assertTrue(server.isOnline());
    }

}