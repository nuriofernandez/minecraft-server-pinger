package me.nurio.minecraft.pinger;

import java.io.IOException;
import java.net.InetSocketAddress;

public class MClass {

    public static void main(String[] args) throws IOException {
        ServerPing server = new ServerPing();
        server.setAddress(new InetSocketAddress("tcpshield.koru.rip", 25565));
        server.setTimeout(500);
        StatusResponse response = server.fetchData();
        System.out.println(response.getDescription());
    }

}