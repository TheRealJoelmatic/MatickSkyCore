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

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class wild implements CommandExecutor {

    private final Map<UUID, String> teleportingPlayers = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }
        Player player = (Player) sender;

        // Check if the player is already in the process of teleportation
        if (teleportingPlayers.containsKey(player.getUniqueId())) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou are already teleporting!"));
            return true;
        }

        World world = player.getWorld();

        double minX = -300;
        double maxX = 300;

        double minZ = -300;
        double maxZ = 300;

        double x = minX + Math.random() * (maxX - minX);
        double z = minZ + Math.random() * (maxZ - minZ);

        double y = world.getHighestBlockYAt((int) x, (int) z) + 1;

        Location randomLocation = new Location(world, x, y, z);
        Location ogPlayerLocation = player.getLocation();

        teleportingPlayers.put(player.getUniqueId(), player.getUniqueId().toString());

        // Make sure they don't move
        new BukkitRunnable() {
            int countdown = 5; // 5 seconds

            @Override
            public void run() {
                if (countdown == 0) {
                    player.teleport(new Location(Bukkit.getWorld("world"), randomLocation.getX(), randomLocation.getY(), randomLocation.getZ()));
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aYou have been teleported to the wild."));

                    // Remove the player from the teleporting map
                    teleportingPlayers.remove(player.getUniqueId());

                    cancel();
                } else {

                    boolean x0k = ogPlayerLocation.getX() < player.getLocation().getX() +  1 && ogPlayerLocation.getX() > player.getLocation().getX() +  - 1;
                    boolean Z0k = ogPlayerLocation.getZ() < player.getLocation().getZ() +  1 && ogPlayerLocation.getZ() > player.getLocation().getZ() +  - 1;

                    if (!(x0k && Z0k)) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cTeleport Canceled"));

                        // Remove the player from the teleporting map
                        teleportingPlayers.remove(player.getUniqueId());

                        cancel();
                    }

                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&fTeleporting in &a" + countdown + " seconds... &c&lDon't Move!"));
                    countdown--;
                }
            }
        }.runTaskTimer(MsClansAddons.getInstance(), 0L, 20L); // Run every second

        return true;
    }
}