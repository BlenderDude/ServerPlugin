package com.danielabdelsamed.ServerPlugin;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ServerListener implements Listener {
    @EventHandler
    public void OnChat(AsyncPlayerChatEvent event) {
        // Get the player
        Player player = event.getPlayer();

        // Create the array of possible colors to assign to players
        ChatColor[] colors = new ChatColor[] { ChatColor.AQUA, ChatColor.BLUE, ChatColor.GOLD, ChatColor.GRAY, ChatColor.GREEN, ChatColor.LIGHT_PURPLE, ChatColor.RED, ChatColor.YELLOW };

        // Find that player's color based on the hash of their unique ID
        ChatColor color = colors[Math.abs(player.getUniqueId().hashCode()) % colors.length];

        // Format the message with their color
        event.setFormat("[" + color + player.getDisplayName() + ChatColor.RESET  + "] " + event.getMessage());
    }
}
