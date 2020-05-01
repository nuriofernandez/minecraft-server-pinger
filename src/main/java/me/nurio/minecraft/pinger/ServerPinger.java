package me.nurio.minecraft.pinger;

import lombok.SneakyThrows;
import me.nurio.minecraft.pinger.beans.StatusResponse;
import me.nurio.minecraft.pinger.utils.SRVResolver;

import java.net.InetSocketAddress;

public class ServerPinger {

    @SneakyThrows
    public static StatusResponse ping(String serverAddress) {
        InetSocketAddress address = SRVResolver.getSocketAddress(serverAddress);
        ServerConnection server = new ServerConnection(address);
        return server.fetchData();
    }

}