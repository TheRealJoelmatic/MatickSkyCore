package org.matic.msclansaddons.commands;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

import static org.matic.msclansaddons.functions.heartManger.setPlayerHealthToSaved;

public class lifesteal implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            System.out.println("Only players can use this command.");
            return false;
        }

        Player player = (Player) sender;

        if (!player.getPlayer().hasPermission("core.ls")){
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lYou dont have the perms for this!"));
            return true;
        }

        if (args[0].equalsIgnoreCase("update")) {
            String playerName = args[1];
            Player targetPlayer = Bukkit.getPlayerExact(playerName);
            if (targetPlayer == null) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lPlayer " + playerName + " is not online."));
                return true;
            }
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aUpdated " + playerName));
            setPlayerHealthToSaved(targetPlayer);
            return true;
        } else if (args[0].equalsIgnoreCase("help")) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&m&3--------------------------------"));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3"));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l MS LifeSteal &7Made by Joelmatic"));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3"));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c/update &3| Update the players Health"));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3"));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&m&3--------------------------------"));
            return true;
        } else if (args[0].equalsIgnoreCase("heart")) {
            if(player.isOp()){
                ItemStack customItem = new ItemStack(Material.RED_DYE);

                ItemMeta meta = customItem.getItemMeta();

                meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',"&c&lHeart"));

                List<String> lore = new ArrayList<>();
                lore.add(ChatColor.translateAlternateColorCodes('&',"&f&oRight Click to redeem"));
                meta.setLore(lore);

                meta.addEnchant(Enchantment.DAMAGE_ALL, 5, true);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                customItem.setItemMeta(meta);

                player.getInventory().addItem(customItem);
                return true;
            }
            return true;
        }
        else{
            sender.sendMessage("Unknown subcommand. Use: /ls help");
            return false;
        }
    }
}
