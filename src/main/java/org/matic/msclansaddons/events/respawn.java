package org.matic.msclansaddons.events;

import org.bukkit.event.entity.PlayerDeathEvent;
import org.matic.msclansaddons.MsClansAddons;
import org.matic.msclansaddons.commands.spawn;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import static org.matic.msclansaddons.functions.heartManger.setPlayerHealthToSaved;

public class respawn implements Listener {

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {

        Player player = event.getPlayer();
        event.setRespawnLocation(new Location(Bukkit.getWorld("world"), spawn.spawnX, spawn.spawnY, spawn.spawnZ));
        player.teleport(new Location(Bukkit.getWorld("world"), spawn.spawnX, spawn.spawnY, spawn.spawnZ));
        setPlayerHealthToSaved(player);
    }

    @EventHandler
    public void PlayerDeathEvent(PlayerDeathEvent event){
        Player player = event.getEntity();
        Bukkit.getScheduler().runTaskLater(MsClansAddons.getInstance(), () -> {
            player.spigot().respawn();
        }, 20);
    }
}

