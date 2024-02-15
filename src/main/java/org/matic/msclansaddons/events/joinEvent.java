package org.matic.msclansaddons.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.matic.msclansaddons.MsClansAddons;

import static org.matic.msclansaddons.data.saveManger.saveData;
import static org.matic.msclansaddons.functions.heartManger.setMaxHearts;
import static org.matic.msclansaddons.functions.heartManger.setPlayerHealthToSaved;
import static org.matic.msclansaddons.util.util.hasPlayerPlayedBefore;

public class joinEvent implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);
        Player player = event.getPlayer();
        if (!event.getPlayer().hasPermission("core.mod")){
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',"&7[&a+&7] " + event.getPlayer().getName()));
        }

        if(hasPlayerPlayedBefore(player.getName())){
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',"&aEveryone welcome " + event.getPlayer().getName()));
            setMaxHearts(player, 20);
        }

        try{
            saveData(player.getName(), player.getUniqueId().toString(),"uuid", "uuid.yml");
            saveData(player.getName(), player.getAddress().getAddress().toString(),"ip", "uuid.yml");
        }catch (Exception e){}

        Bukkit.getScheduler().runTaskLater(MsClansAddons.getInstance(), () -> {
            setPlayerHealthToSaved(player);
        }, 20);
    }
}
