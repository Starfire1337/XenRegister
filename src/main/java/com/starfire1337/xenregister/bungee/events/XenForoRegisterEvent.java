package com.starfire1337.xenregister.bungee.events;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Event;

import java.util.List;

public class XenForoRegisterEvent extends Event {

    private ProxiedPlayer player;
    private List<String> commands;

    public XenForoRegisterEvent(ProxiedPlayer player, List<String> commands) {
        this.player = player;
        this.commands = commands;
    }

    public ProxiedPlayer getPlayer() {
        return this.player;
    }

    public List<String> getCommands() {
        return this.commands;
    }

}
