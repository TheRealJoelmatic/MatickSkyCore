package org.matic.msclansaddons.functions;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import static org.matic.msclansaddons.data.saveManger.getData;
import static org.matic.msclansaddons.data.saveManger.saveData;

public class heartManger {

    public static void setMaxHearts(Player player, double maxHearts) {
        player.setHealthScale(maxHearts);
        saveData(player.getUniqueId().toString(), maxHearts,"hearts", "playerdata.yml");
    }

    public static void killHandler(Player killed, Player killer){

        if(killed.getAddress() == killer.getAddress()){
            killer.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lYou are unable to gain or lose a heart when eliminating someone who shares the same IP address as you!"));
            killed.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lYou are unable to gain or lose a heart when eliminating someone who shares the same IP address as you!"));
            return;
        }

        double killedNewHealth = killed.getHealthScale() - 2;
        double killerNewHealth = killer.getHealthScale() + 2;

        setMaxHearts(killed, killedNewHealth);
        setMaxHearts(killer, killerNewHealth);
    }

    public static void setPlayerHealthToSaved(Player player){
        double savedPlayerHealth = getData(player.getUniqueId().toString(), "hearts", "playerdata.yml");

        if (String.valueOf(savedPlayerHealth).equals("0.0")) {
            ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
            Bukkit.dispatchCommand(console, "kick " + player.getName() + " You have no hearts left :(");
            return;
        }
        player.setHealthScale(savedPlayerHealth);
    }

    public static double getHealth(Player player){
        double playerHealth = getData(player.getUniqueId().toString(), "hearts", "playerdata.yml");
        if (playerHealth == 0.0){
            return 20.0;
        }
        return playerHealth;
    }
}
