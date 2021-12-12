package com.danielabdelsamed.ServerPlugin;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class VoteManager {
    // Singleton storage
    private static VoteManager i = null;

    // Timer and vote setup
    private Timer timer = new Timer();
    private final Set<Player> activeVotes = new HashSet<>();

    // Chat prefix
    private final String prefix = "[" + ChatColor.BLUE + "StopRain" + ChatColor.RESET + "] ";

    public void addVote(Player player){
        // Get the server from the player
        Server server = player.getServer();

        // Get the total player count in the Overworld
        int totalPlayers = server.getWorlds().get(0).getPlayers().size();

        // Add the player's vote
        activeVotes.add(player);

        // Determine the amount of votes needed from the percent required and the player count
        int percentRequired = 50;
        int votesNeeded = (int) Math.ceil(totalPlayers * (percentRequired / 100.0) - activeVotes.size());

        // If more than one vote is needed
        if (votesNeeded > 0) {
            // Display current voting state
            server.broadcastMessage( prefix + ChatColor.YELLOW + player.getDisplayName() + ChatColor.WHITE + " has voted to stop the rain.");
            server.broadcastMessage( prefix + ChatColor.RED + votesNeeded + ChatColor.WHITE + " more vote"+ (votesNeeded != 1 ? "s" : "") + " needed");
            // Set up a timer that cancels the vote after 60 seconds
            clearVotes();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    activeVotes.clear();
                    server.broadcastMessage(prefix + "Stop rain vote has been cancelled (60 seconds elapsed)");
                }
            }, 60 * 1000);
            return;
        }

        // If vote count is reached or the player is the only one, set the world to clear for 1 hour
        int clearDuration = 20 * 60 * 60;
        server.getWorlds().get(0).setClearWeatherDuration(clearDuration);
        server.broadcastMessage(prefix + "Rain has been "+ ChatColor.AQUA + "cleared!" + ChatColor.WHITE);

        // Clear all the votes
        this.clearVotes();
    }

    public void clearVotes() {
        // Clear vote set and purge/reset timers
        activeVotes.clear();
        try {
            timer.cancel();
            timer.purge();
        } catch (Error e) {
            //
        } finally {
            timer = new Timer();
        }
    }

    // Returns a singleton instance
    public static VoteManager getInstance()
    {
        if(i == null)
            i = new VoteManager();
        return i;
    }
}
