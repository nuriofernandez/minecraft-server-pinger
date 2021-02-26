# Minecraft Server Pinger
Minecraft server pinger library obtains information such as the MOTD of minecraft servers.

# Maven dependency

```xml
<repository>
    <id>nurio-repo</id>
    <url>https://nurio.me/repo/mvn/</url>
</repository>
```

```xml
<dependency>
    <groupId>me.nurio.minecraft</groupId>
    <artifactId>pinger</artifactId>
    <version>0.0.2</version>
    <scope>compile</scope>
</dependency>
```

# How to use the server pinger

The `MinecraftServerPinger` class it's very easy to use, it will manage everything for you, SRV domain resolve, offline check, getting server's data, connection timeout management...

Here is an example for you:

```java
MinecraftServerStatus server = MinecraftServerPinger.ping("serveradress.com:25544");
// In case the server is not online:
if(server.isOffline()) {
   System.out.println("Minecraft server 'serveradress.com' is not online!");
   
   // You can get the server connection exception like that:
   OfflineMinecraftServerStatus offlineServer = (OfflineMinecraftServerStatus) server;
   offlineServer.getException().printStackTrace();
   return;
}

// These methods will throw an exceptions in case the server is not online.
String motd = server.getMotd();
long latency = server.getPing();
Version version = server.getVersion();
Players players = server.getPlayers();
String base64Favicon = server.getFavicon();
```

# Additional utilities

## Advanced server query mode

If you want to have a little more control of this process you can use the `MinecraftServerConnection` class.
You will need to provide a `InetSocketAddress` and optionally a connection timeout time in milliseconds.

Here is an example:

```java
InetSocketAddress socketAddress = new InetSocketAddress("serveradress.com", 25565);

MinecraftServerConnection connection = new MinecraftServerConnection(socketAddress);
connection.setTimeout(5000); // Optional timeout (default was 1000ms)
MinecraftServerStatus server = connection.fetchData();

// Here you can continue as the first example. (isOffline(), getMotd()...)
```

## SRVResolver resolver

Some minecraft servers uses SRV domain records to manage server's port.
With the SRVResolver utility class, you can obtian the server real domain and port.

```java
InetSocketAddress address = SRVResolver.getSocketAddress(serverAddress);
String serverAddress = address.getHostName();
int serverPort = address.getPort();
```
