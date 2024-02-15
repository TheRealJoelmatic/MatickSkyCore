package org.matic.msclansaddons.events;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;

import static org.matic.msclansaddons.functions.heartManger.getHealth;
import static org.matic.msclansaddons.functions.heartManger.setMaxHearts;

public class interact implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        checkForHeart(event);
    }

    public boolean checkForHeart(PlayerInteractEvent event) {
        if (!(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
            return false;
        }

        ItemStack item = event.getItem();
        if (!(item != null && item.getType() == Material.RED_DYE && item.hasItemMeta())) {
            return false;
        }

        ItemMeta meta = item.getItemMeta();
        if (!(meta.hasDisplayName() && meta.getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&c&lHeart")))) {
            return false;
        }

        Map<Enchantment, Integer> enchantments = meta.getEnchants();
        if (!(enchantments.containsKey(Enchantment.DAMAGE_ALL) && enchantments.get(Enchantment.DAMAGE_ALL) == 5)) {
            return false;
        }

        Player player = event.getPlayer();
        int amount = item.getAmount();

        event.setCancelled(true);

        setMaxHearts(player, getHealth(player) + 2);
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&lGranted one Heart!"));

        if (amount > 1) {
            item.setAmount(amount - 1);
        } else {
            player.getInventory().removeItem(item);
        }

        return true;
    }
}
