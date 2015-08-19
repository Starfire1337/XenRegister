package com.starfire1337.xenregister.spigot;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class XenRegister extends JavaPlugin {

    private static Plugin plugin;

    @Override
    public void onEnable() {

        plugin = this;

        saveDefaultConfig();

        getServer().getScheduler().runTaskAsynchronously(this, new Runnable() {
            @Override
            public void run() {
                ConnectionListener.startServer();
            }
        });

    }

    @Override
    public void onDisable() {

        ConnectionListener.stopServer = true;

        plugin = null;

    }

    public static Plugin getInstance() {

        return plugin;

    }

}
