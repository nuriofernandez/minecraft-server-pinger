package me.nurio.minecraft.pinger;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class ServerPing {

    @Getter @Setter private InetSocketAddress address;
    @Getter @Setter private int timeout = 7000;
    private Gson gson = new Gson();

    public void setAddress(String hostname, int port) {
        this.address = new InetSocketAddress(hostname, port);
    }

    public StatusResponse fetchData() throws IOException, SocketException, SocketTimeoutException {
        Socket socket = new Socket();
        socket.setSoTimeout(this.timeout);

        socket.connect(this.address, this.timeout);

        OutputStream outputStream = socket.getOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

        InputStream inputStream = socket.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream handshake = new DataOutputStream(b);
        handshake.writeByte(0);
        writeVarInt(handshake, 4);
        writeVarInt(handshake, this.address.getHostString().length());
        handshake.writeBytes(this.address.getHostString());
        handshake.writeShort(this.address.getPort());
        writeVarInt(handshake, 1);

        writeVarInt(dataOutputStream, b.size());
        dataOutputStream.write(b.toByteArray());

        dataOutputStream.writeByte(1);
        dataOutputStream.writeByte(0);
        DataInputStream dataInputStream = new DataInputStream(inputStream);

        int size = readVarInt(dataInputStream);
        int id = readVarInt(dataInputStream);

        if (id == -1) throw new IOException("Premature end of stream.");
        if (id != 0) throw new IOException("Invalid packetID");

        int length = readVarInt(dataInputStream);
        if (length == -1) throw new IOException("Premature end of stream.");
        if (length == 0) throw new IOException("Invalid string length.");

        byte[] in = new byte[length];
        dataInputStream.readFully(in);
        String json = new String(in);

        long now = System.currentTimeMillis();
        dataOutputStream.writeByte(9);
        dataOutputStream.writeByte(1);
        dataOutputStream.writeLong(now);

        readVarInt(dataInputStream);
        id = readVarInt(dataInputStream);
        if (id == -1) throw new IOException("Premature end of stream.");
        if (id != 1) throw new IOException("Invalid packetID");

        long pingtime = dataInputStream.readLong();

        StatusResponse response = new StatusResponse();
        response.setTime((int) (now - pingtime)); // Always zero
        response.setJson(json);

        dataOutputStream.close();
        outputStream.close();
        inputStreamReader.close();
        inputStream.close();
        socket.close();

        return response;
    }

    private int readVarInt(DataInputStream in)
        throws IOException {
        int i = 0;
        int j = 0;
        int k;
        do {
            k = in.readByte();
            i |= (k & 0x7F) << j++ * 7;
            if (j > 5) {
                throw new RuntimeException("VarInt too big");
            }
        } while ((k & 0x80) == 128);
        return i;
    }

    private void writeVarInt(DataOutputStream out, int paramInt)
        throws IOException {
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
