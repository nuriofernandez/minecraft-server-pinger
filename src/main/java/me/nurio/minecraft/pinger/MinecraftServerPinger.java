package me.nurio.minecraft.pinger;

import me.nurio.minecraft.pinger.beans.MinecraftServerStatus;
import me.nurio.minecraft.pinger.beans.OfflineMinecraftServerStatus;
import me.nurio.minecraft.pinger.utils.SRVResolver;

import java.net.InetSocketAddress;

public class MinecraftServerPinger {

    /**
     * Pings provided server address looking for a Minecraft server.
     * This method will check if the server is using an SRV record and will solve it.
     * <p>
     * In case the server is offline, this method will return an {@code OfflineMinecraftServerStatus} instance.
     *
     * @param serverAddress Minecraft server address to fetch.
     * @return MinecraftServerStatus class with the results data.
     */
    public static MinecraftServerStatus ping(String serverAddress) {
        try {
            InetSocketAddress address = SRVResolver.getSocketAddress(serverAddress);

            MinecraftServerConnection server = new MinecraftServerConnection(address);
            return server.fetchData();
        } catch (Exception exception) {
            // In case the fetchData fails, return an Offline server instance.
            return new OfflineMinecraftServerStatus(exception);
        }
    }

}