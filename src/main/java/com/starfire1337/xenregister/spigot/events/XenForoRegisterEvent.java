package com.starfire1337.xenregister.spigot.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.List;

public class XenForoRegisterEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private List<String> commands;

    public XenForoRegisterEvent(Player player, List<String> commands) {
        this.player = player;
        this.commands = commands;
    }

    public Player getPlayer() {
        return this.player;
    }

    public List<String> getCommands() {
        return this.commands;
    }

    public void setCommands(List<String> commands) {
        this.commands = commands;
    }

    public void addCommand(String command) {
        this.commands.add(command);
    }

    public HandlerList getHandlers() {
        return handlers;
    }

}
