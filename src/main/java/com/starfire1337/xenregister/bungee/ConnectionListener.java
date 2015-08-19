package com.starfire1337.xenregister.bungee;

import com.starfire1337.xenregister.SocketUtils;
import com.starfire1337.xenregister.bungee.events.XenForoRegisterEvent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class ConnectionListener {

    public static boolean stopServer = false;

    public static void startServer() {
        SocketUtils socketUtils = new SocketUtils(XenRegister.getConfig().getString("password"));
        try {
            ServerSocket socket = new ServerSocket(XenRegister.getConfig().getInt("port"));
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
                            List<String> commands = XenRegister.getConfig().getStringList("commands");
                            ProxiedPlayer player = XenRegister.getInstance().getProxy().getPlayer(p);
                            if(player == null) {
                                return;
                            }
                            XenForoRegisterEvent event = new XenForoRegisterEvent(player, commands);
                            XenRegister.getInstance().getProxy().getPluginManager().callEvent(event);

                            for(String command : commands) {
                                XenRegister.getInstance().getProxy().getPluginManager().dispatchCommand(XenRegister.getInstance().getProxy().getConsole(), command.replace("{name}", p));
                            }
                        }
                    } else {
                        XenRegister.getInstance().getProxy().getLogger().info("Invalid authentication attempt from " + cSocket.getInetAddress() + ", Data: " + msg);
                    }
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
