package me.nurio.minecraft.pinger.utils;

import lombok.SneakyThrows;
import org.xbill.DNS.Lookup;
import org.xbill.DNS.Record;
import org.xbill.DNS.SRVRecord;
import org.xbill.DNS.Type;

import java.net.InetSocketAddress;

public class SRVResolver {

    @SneakyThrows
    public static InetSocketAddress getSocketAddress(String domain) {
        return getSocketAddress(getHost(domain), getPort(domain));
    }

    @SneakyThrows
    public static InetSocketAddress getSocketAddress(String domain, int port) {
        Record[] records = new Lookup("_minecraft._tcp." + domain, Type.SRV).run();
        if (records != null && records.length >= 1) {
            SRVRecord srv = (SRVRecord) records[0];
            String hostname = srv.getTarget().toString().replaceFirst("\\.$", "");
            return new InetSocketAddress(hostname, srv.getPort());
        }
        return new InetSocketAddress(domain, port);
    }

    private static String getHost(String serverAddress) {
        if (!serverAddress.contains(":")) return serverAddress;
        return serverAddress.split(":")[0];
    }

    private static int getPort(String serverAddress) {
        if (!serverAddress.contains(":")) return 25565;
        return Integer.parseInt(serverAddress.split(":")[1]);
    }

}