package org.matic.msclansaddons.events;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;


public class move implements Listener {
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Block blockBelow = player.getLocation().subtract(0, 1, 0).getBlock();

        if (blockBelow.getType() == Material.SLIME_BLOCK) {
            Vector direction = player.getLocation().getDirection().normalize();


            double horizontalForce = 1.0;
            double verticalForce = 1.0;

            Vector launchVelocity = new Vector(direction.getX() * horizontalForce, verticalForce, direction.getZ() * horizontalForce);

            player.setVelocity(launchVelocity);
        }
    }
}
