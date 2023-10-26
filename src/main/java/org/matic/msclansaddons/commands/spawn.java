package org.matic.msclansaddons.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.matic.msclansaddons.MsClansAddons;

public class spawn implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            System.out.println("Only players can use this command.");
            return false;
        }

        Player player = (Player) sender;

        // Replace these coordinates with the actual spawn point coordinates
        double spawnX = 0.5;
        double spawnY = 190.2;
        double spawnZ = 0.5;

        Location ogPlayerLocation = player.getLocation();

        // Make sure they don't move
        new BukkitRunnable() {
            int countdown = 5; // 5 seconds

            @Override
            public void run() {
                if (countdown == 0) {
                    player.teleport(new Location(Bukkit.getWorld("world"), spawnX, spawnY, spawnZ));
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3MS &7| &aYou have been teleported to the spawn point."));
                    cancel();
                } else {
                    if (!(ogPlayerLocation.getX() == player.getLocation().getX() && ogPlayerLocation.getZ() == player.getLocation().getZ())) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3MS &7| &cTeleport Canceled"));
                        cancel();
                    }

                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3MS &7| &fTeleporting in &a" + countdown + " seconds... &c&lDon't Move!"));
                    countdown--;
                }
            }
        }.runTaskTimer(MsClansAddons.getInstance(), 0L, 20L); // Run every second

        return true;
    }
}
