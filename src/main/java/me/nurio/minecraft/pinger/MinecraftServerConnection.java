package me.nurio.minecraft.pinger;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.nurio.minecraft.pinger.beans.MinecraftServerStatus;
import me.nurio.minecraft.pinger.beans.OnlineMinecraftServerStatus;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * This class manages Minecraft server list handshake.
 * https://wiki.vg/Server_List_Ping#Handshake
 */
@RequiredArgsConstructor
public class MinecraftServerConnection {

    private ObjectMapper mapper = new ObjectMapper() {
        {
            disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        }
    };

    @NonNull private InetSocketAddress address;
    @Setter private int timeout = 1000;

    private Socket socket = new Socket();
    private OutputStream outputStream;
    private DataOutputStream dataOutputStream;
    private InputStream inputStream;
    private InputStreamReader inputStreamReader;
    private DataInputStream dataInputStream;

    private void connect() throws IOException {
        socket.setSoTimeout(this.timeout);
        socket.connect(this.address, this.timeout);

        outputStream = socket.getOutputStream();
        dataOutputStream = new DataOutputStream(outputStream);
        inputStream = socket.getInputStream();
        inputStreamReader = new InputStreamReader(inputStream);
        dataInputStream = new DataInputStream(inputStream);
    }

    private void disconnect() throws IOException {
        dataOutputStream.close();
        outputStream.close();
        inputStreamReader.close();
        inputStream.close();
        socket.close();
    }

    private String handshake() throws IOException {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream handshake = new DataOutputStream(b);
        handshake.writeByte(0x00);

        // Protocol version
        writeInteger(handshake, 4);
        // Server Address
        writeInteger(handshake, address.getHostString().length());
        handshake.writeBytes(address.getHostString());
        // Server Port
        handshake.writeShort(address.getPort());
        // Next state
        writeInteger(handshake, 1);
        writeInteger(dataOutputStream, b.size());
        dataOutputStream.write(b.toByteArray());

        dataOutputStream.writeByte(0x01);
        dataOutputStream.writeByte(0x00);

        int size = readInteger();
        int id = readInteger();
        if (id == -1) throw new IOException("Premature end of stream");
        if (id != 0) throw new IOException("Invalid packet id");
        int length = readInteger();
        if (length == -1) throw new IOException("Premature end of stream");
        if (length == 0) throw new IOException("Invalid string length");

        byte[] in = new byte[length];
        dataInputStream.readFully(in);
        return new String(in);
    }

    public MinecraftServerStatus fetchData() throws IOException {
        connect();

        long start = System.currentTimeMillis();
        String json = handshake();
        long time = System.currentTimeMillis() - start;

        disconnect();

        OnlineMinecraftServerStatus response = mapper.readValue(json, OnlineMinecraftServerStatus.class);
        response.setPing(time);

        return response;
    }

    private int readInteger() throws IOException {
        int i = 0;
        int j = 0;
        int k;
        do {
            k = dataInputStream.readByte();
            i |= (k & 0x7F) << j++ * 7;
            if (j > 5) throw new RuntimeException("VarInt too big");
        } while ((k & 0x80) == 128);
        return i;
    }

    private void writeInteger(DataOutputStream out, int paramInt) throws IOException {
        for (; ; ) {
            if ((paramInt & 0xFFFFFF80) == 0) {
                out.writeByte(paramInt);
                return;
            }
            out.writeByte(paramInt & 0x7F | 0x80);
            paramInt >>>= 7;
        }
    }

}