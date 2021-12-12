package com.danielabdelsamed.ServerPlugin;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;


public class ServerPlugin extends JavaPlugin implements Listener
{
    public final ServerListener listener = new ServerListener();

    @Override()
    public void onEnable() {
        // Note the server is enabled
        getLogger().info("ServerPlugin enabled!");

        // Register the server event listener
        getServer().getPluginManager().registerEvents(listener, this);

        // Register the stoprain command
        this.getCommand("stoprain").setExecutor(new CommandStopRain());
    }

    @Override()
    public void onDisable() {
        getLogger().info("ServerPlugin disabled!");
    }

}