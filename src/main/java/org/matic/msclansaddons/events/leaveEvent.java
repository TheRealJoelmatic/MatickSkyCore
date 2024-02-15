package org.matic.msclansaddons.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import static org.matic.msclansaddons.data.saveManger.saveData;

public class leaveEvent  implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        // Prevent the default quit message
        event.setQuitMessage(null);

        // Broadcast a custom leave message to all players
        if (!event.getPlayer().hasPermission("core.mod")) {
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&7[&c-&7] " + event.getPlayer().getName()));
        }

        saveData(event.getPlayer().getUniqueId().toString(), event.getPlayer().getHealthScale(),"hearts", "playerdata.yml");
    }
}