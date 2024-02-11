package org.matic.msclansaddons.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static org.matic.msclansaddons.data.saveManger.saveData;

public class joinEvent implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);
        if (!event.getPlayer().hasPermission("core.mod")){
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',"&7[&a+&7] " + event.getPlayer().getName()));
        }


        Player player = event.getPlayer();

        try{
            saveData(player.getName(), player.getUniqueId().toString(),"uuid", "uuid.yml");
            saveData(player.getName(), player.getAddress().getAddress().toString(),"ip", "uuid.yml");
        }catch (Exception e){}
    }
}
