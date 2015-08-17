package com.starfire1337.XenRegister.Socket;

import com.starfire1337.XenRegister.Util.SocketUtils;
import com.starfire1337.XenRegister.XenRegister;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class ConnectionListener {

    public static boolean stopServer = false;

    public static void startServer() {
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
                    if(SocketUtils.checkAuth(msg)) {
                        final String p = SocketUtils.getUser(msg);
                        if(!p.isEmpty()) {
                            XenRegister.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(XenRegister.getInstance(), new Runnable() {
                                public void run() {
                                    List<String> commands = (List<String>) XenRegister.getInstance().getConfig().getList("commands");
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

