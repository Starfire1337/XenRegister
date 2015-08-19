package com.starfire1337.xenregister.bungee;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class XenRegister extends Plugin {

    private static Plugin plugin;
    private static Configuration config;

    @Override
    public void onEnable() {

        plugin = this;

        try {
            if (!getDataFolder().exists())
                getDataFolder().mkdir();

            File file = new File(getDataFolder(), "config.yml");

            if (!file.exists())
                Files.copy(getResourceAsStream("config-bungee.yml"), file.toPath());

            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "config.yml"));

        } catch (IOException e) {
            e.printStackTrace();
        }

        getProxy().getScheduler().runAsync(this, new Runnable() {
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

    public static Configuration getConfig() {
        return config;
    }

}
