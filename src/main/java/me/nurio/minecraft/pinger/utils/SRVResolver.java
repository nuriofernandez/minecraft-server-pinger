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
        return getSocketAddress(domain, 25565);
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

}