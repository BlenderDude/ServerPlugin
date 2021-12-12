package com.danielabdelsamed.ServerPlugin;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandStopRain implements CommandExecutor {
    private final String prefix = ChatColor.BLUE + "[StopRain] " + ChatColor.WHITE;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player player) {
            // If the player isn't in the over world tell them to fuck off
            if (player.getWorld() != player.getServer().getWorlds().get(0)) {
                player.sendMessage(prefix + "You aren't in the Overworld you fucking doofus");
                return true;
            }

            // If the world is already sunny, also politely tell them to fuck off
            if (player.getServer().getWorlds().get(0).isClearWeather()) {
                player.sendMessage(prefix + "It's literally already sunny you fucking moron");
                return true;
            }

            // Grab the vote manager singleton and add the player's vote
            VoteManager.getInstance().addVote(player);

            return true;
        }

        return true;
    }
}
