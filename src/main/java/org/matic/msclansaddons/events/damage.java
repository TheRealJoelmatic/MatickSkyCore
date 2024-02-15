package org.matic.msclansaddons.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import static org.matic.msclansaddons.functions.heartManger.setPlayerHealthToSaved;

public class damage implements Listener {

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            setPlayerHealthToSaved(player);
        }
    }
}
