package com.danielabdelsamed.ServerPlugin;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class ServerListener implements Listener {

    private ChatColor getPlayerColor(Player player) {
        // Create the array of possible colors to assign to players
        ChatColor[] colors = new ChatColor[] { ChatColor.AQUA, ChatColor.BLUE, ChatColor.GOLD, ChatColor.GRAY, ChatColor.GREEN, ChatColor.LIGHT_PURPLE, ChatColor.RED, ChatColor.YELLOW };

        // Find that player's color based on the hash of their unique ID
        return colors[Math.abs(player.getUniqueId().hashCode()) % colors.length];
    }

    private String cfmt(String s, ChatColor c) {
        return c + s + ChatColor.RESET;
    }

    @EventHandler
    public void OnChat(AsyncPlayerChatEvent event) {
        // Get the player
        Player player = event.getPlayer();

        // Get player's color
        ChatColor color = getPlayerColor(player);

        // Get all players and remove sender
        Set<Player> players = new HashSet<>(event.getPlayer().getServer().getOnlinePlayers());
        players.remove(event.getPlayer());

        // If there are no other players, send generic message
        if (players.size() == 0) {
            event.setFormat("[" + cfmt(player.getDisplayName(), color) + "] " + event.getMessage() + (event.getMessage().endsWith(".") ? "" : ".") + " I also "+ cfmt("looooooove", ChatColor.RED) + " cum.");
            return;
        }

        // Pick other player
        Player otherPlayer = players.toArray(new Player[0])[new Random().nextInt(players.size())];
        ChatColor otherPlayerColor = getPlayerColor(otherPlayer);

        // Format the message with their color
        event.setFormat("[" + cfmt(player.getDisplayName(), color) + "] " + event.getMessage() + (event.getMessage().endsWith(".") ? "" : ".") + " I also " + cfmt("looooooove ", ChatColor.RED) + cfmt(otherPlayer.getDisplayName(), otherPlayerColor) + "'s cum.");
    }

    @EventHandler
    public void OnDeath(PlayerDeathEvent event) {
        Player killed = event.getEntity();
        if(killed.getLastDamageCause() instanceof EntityDamageByEntityEvent dmgEvent) {
            if(dmgEvent.getDamager() instanceof Player killer) {
                event.setDeathMessage(cfmt(killer.getDisplayName(), getPlayerColor(killer)) + " demolished " + cfmt(killed.getDisplayName(), getPlayerColor(killed)) + " with their " + cfmt("enormous", ChatColor.BOLD) + " dong.");
            }
        }
    }

    @EventHandler
    public void OnEnterBed(PlayerBedEnterEvent e) {
        int sleepingPlayers = 0;
        double threshold = 0.5;
        List<Player> players = e.getPlayer().getServer().getWorlds().get(0).getPlayers();
        for(Player player : players) {
            if(player.isSleeping()) {
                sleepingPlayers++;
            }
        }
        sleepingPlayers++;
        int sleepingPlayersNeeded = (int) Math.floor(players.size() * threshold);
        e.getPlayer().getServer().broadcastMessage("["+cfmt("Sleep", ChatColor.GRAY)+"] " + e.getPlayer().getDisplayName() + " has started sleeping " + cfmt("(" + sleepingPlayers + "/" + sleepingPlayersNeeded + ")", ChatColor.YELLOW));
        if(sleepingPlayers < sleepingPlayersNeeded) {
            return;
        }

        e.getPlayer().getServer().getWorlds().get(0).setTime(0);
        e.getPlayer().getServer().broadcastMessage("["+cfmt("Sleep", ChatColor.GRAY)+"] Good morning!");
    }
}
