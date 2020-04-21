package me.nurio.minecraft.pinger;

import java.io.IOException;
import java.net.InetSocketAddress;

public class MClass {

    public static void main(String[] args) throws IOException {
        ServerPing server = new ServerPing();
        server.setAddress(new InetSocketAddress("connect.aemine.vn", 25565));
        server.setTimeout(500);

        StatusResponse response = server.fetchData();
        System.out.println(response.getJson());
        System.out.println(response.getTime());
    }

}