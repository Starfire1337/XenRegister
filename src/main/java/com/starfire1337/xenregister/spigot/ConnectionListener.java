package com.starfire1337.xenregister.spigot;

import com.starfire1337.xenregister.SocketUtils;
import com.starfire1337.xenregister.spigot.events.XenForoRegisterEvent;
import org.bukkit.entity.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class ConnectionListener {

    public static boolean stopServer = false;

    public static void startServer() {
        SocketUtils socketUtils = new SocketUtils(XenRegister.getInstance().getConfig().getString("password"));
        try {
            ServerSocket socket = new ServerSocket(XenRegister.getInstance().getConfig().getInt("port"));
            while(true) {
                if(stopServer) {
                    socket.close();
                    break;
                }
                Socket cSocket = socket.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(cSocket.getInputStream()));
                String msg = in.readLine();
                if(!msg.isEmpty()) {
                    if(socketUtils.checkAuth(msg)) {
                        final String p = socketUtils.getUser(msg);
                        if(!p.isEmpty()) {
                            XenRegister.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(XenRegister.getInstance(), new Runnable() {
                                public void run() {
                                    List<String> commands = XenRegister.getInstance().getConfig().getStringList("commands");
                                    Player player = XenRegister.getInstance().getServer().getPlayer(p);
                                    if(player == null) {
                                        return;
                                    }
                                    XenForoRegisterEvent event = new XenForoRegisterEvent(player, commands);
                                    XenRegister.getInstance().getServer().getPluginManager().callEvent(event);
                                    commands = event.getCommands();
                                    for(String command : commands) {
                                        XenRegister.getInstance().getServer().dispatchCommand(XenRegister.getInstance().getServer().getConsoleSender(), command.replace("{name}", p));
                                    }
                                }
                            });
                        }
                    } else {
                        XenRegister.getInstance().getLogger().info("Invalid authentication attempt from " + cSocket.getInetAddress() + ", Data: " + msg);
                    }
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}

