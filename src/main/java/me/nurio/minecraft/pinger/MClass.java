package me.nurio.minecraft.pinger;

import java.io.IOException;

public class MClass {

    public static void main(String[] args) throws IOException {
        StatusResponse response = ServerPinger.ping("koru.rip");
        System.out.println(response.getJson());
        System.out.println(response.getTime());
    }

}