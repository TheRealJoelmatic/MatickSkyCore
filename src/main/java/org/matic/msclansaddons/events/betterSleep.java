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

        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lSleeping is disabled"));

        event.setCancelled(true);
    }
}