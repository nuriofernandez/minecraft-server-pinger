# Minecraft Server Pinger
Minecraft server pinger library obtains information such as the MOTD of minecraft servers.

Usage example:
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
