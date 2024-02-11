package org.matic.msclansaddons.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

import static org.matic.msclansaddons.data.saveManger.getData;
import static org.matic.msclansaddons.data.saveManger.saveData;

public class stats implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            System.out.println("Only players can use this command.");
            return false;
        }

        Player player = (Player) sender;

        String playerName = player.getName();
        String playerUUID = player.getUniqueId().toString();
        int
                kills = 0,
                deaths = 0,
                money = 0,
                points = 0;


        if (args.length == 1) {
            playerName = args[0];
            playerUUID = String.valueOf(getData(playerName, "uuid", "uuid.yml"));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l " + playerUUID));
            if (playerUUID == null || playerUUID.equals("0") || playerUUID.equals("0.0")){
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&l" + playerName + "&r&c not found!"));
                return true;
            }
        }

        kills = GetPlayerKills(playerUUID);
        deaths = GetPlayerDeaths(playerUUID);
        money = GetPlayerEco(playerUUID);
        points = calculatePoints(money, kills, deaths);

        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&m&3--------------------------------"));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&l&f" + playerName + " 's &r&fstats"));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3Kills : &f" + kills));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3Deaths : &f" + deaths));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3Money : &f" + money));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3Points : &f" + points));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&m&3--------------------------------"));

        return true;
    }

    public int GetPlayerKills(String UUID){
        return (int) getData(UUID,"kills", "playerdata.yml");
    }

    public int GetPlayerDeaths(String UUID){
        return (int) getData(UUID,"deaths", "playerdata.yml");
    }

    public int GetPlayerEco(String UUID){
        File playerDataFile = new File("plugins/BetterEconomy/balances.yml");
        YamlConfiguration playerData = YamlConfiguration.loadConfiguration(playerDataFile);
        if (playerData.contains(UUID)) {
            return (int) playerData.getDouble(UUID);
        }
        else{
            return 0;
        }
    }
    
    public int calculatePoints(int money, int kills, int deaths){
        return (money / 100000) + (kills * 4) - deaths;
    }

}
