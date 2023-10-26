package org.matic.msclansaddons.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.matic.msclansaddons.MsClansAddons;

public class wild implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        Player player = (Player) sender;
        World world = player.getWorld();

        // Calculate random coordinates within a 500x500 area
        double minX = 70;
        double maxX = 500;

        double minZ = 70;
        double maxZ = 500;

        double x = minX + Math.random() * (maxX - minX);
        double z = minZ + Math.random() * (maxZ - minZ);

        double y = world.getHighestBlockYAt((int) x, (int) z);

        Location randomLocation = new Location(world, x, y, z);
        Location ogPlayerLocation = player.getLocation();

        // Make sure they don't move
        new BukkitRunnable() {
            int countdown = 5; // 5 seconds

            @Override
            public void run() {
                if (countdown == 0) {
                    player.teleport(new Location(Bukkit.getWorld("world"), randomLocation.getX(), randomLocation.getY(), randomLocation.getZ()));
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3MS &7| &aYou have been teleported to the wild."));
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