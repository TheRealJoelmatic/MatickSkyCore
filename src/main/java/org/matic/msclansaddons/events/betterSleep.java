package org.matic.msclansaddons.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.entity.Player;
public class betterSleep implements Listener{
    @EventHandler
    public void onPlayerBedEnter(PlayerBedEnterEvent event) {
        Player player = event.getPlayer();

        if (event.getBedEnterResult() == PlayerBedEnterEvent.BedEnterResult.OK) {
            // The player is successfully entering the bed to sleep
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&a" + player.getDisplayName() + " &fIs now sleeping. &7(Setting it to day)"));
            World world = player.getWorld();
            if (world != null) {
                world.setTime(0);
                world.setThundering(false);
                world.setStorm(false);
            }
        }
    }
}