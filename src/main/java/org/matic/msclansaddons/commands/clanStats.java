package org.matic.msclansaddons.commands;

import Clans.ClanConfiguration;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.matic.msclansaddons.MsClansAddons;

import java.io.File;
import java.util.*;
import java.util.Map.Entry;

import static org.matic.msclansaddons.data.saveManger.getData;


public class clanStats implements Listener {

    @EventHandler
    public void onOtherPluginCommand(PlayerCommandPreprocessEvent event) {
        if (event.getPlayer() == null) {
            event.getPlayer().sendMessage("Only players can do this!");
            event.setCancelled(true);
            return;
        }
        String command = event.getMessage().substring(1);
        String[] args = command.split(" ");

        if (args.length > 2 && args[0].equalsIgnoreCase("clan") && args[1].equalsIgnoreCase("stats") && !(args[2].isEmpty())) {
            event.setCancelled(true);
            ClanConfiguration clanConfiguration = new ClanConfiguration();
            getPointsMethod(event.getPlayer(), args[2]);
        } else if (args.length > 1 && args[0].equalsIgnoreCase("clan") && args[1].equalsIgnoreCase("stats")) {
            event.setCancelled(true);
            ClanConfiguration clanConfiguration = new ClanConfiguration();
            getPointsMethod(event.getPlayer(), clanConfiguration.getClan(event.getPlayer()));
        }

        if (args.length > 1 && args[0].equalsIgnoreCase("clan") && args[1].equalsIgnoreCase("top")) {
            event.setCancelled(true);
            ClanConfiguration clanConfiguration = new ClanConfiguration();
            Map<String, Double> clansTop = getClanTop();

            Player player = event.getPlayer();

            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&m&3--------------------------------"));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lCLAN TOP"));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', " "));

            int loopNumber = 1;
            for (Map.Entry<String, Double> entry : clansTop.entrySet()) {
                String name = entry.getKey();
                Double value = entry.getValue();

                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3 " + loopNumber + " - " + name + " - " + value + " Points"));

                loopNumber++;
            }


            player.sendMessage(ChatColor.translateAlternateColorCodes('&', " "));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&m&m&3--------------------------------"));

        }


        return;

    }

    public void getPointsMethod(Player player,String clan){

        ClanConfiguration clanConfiguration = new ClanConfiguration();

        Double clanKills = (double) 0;
        Double clanDeaths = (double) 0;
        Double clanMoney = (double) 0;

        Economy economy = MsClansAddons.getEconomy();

        if(clanConfiguration.getClan(clan) == null){
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3Clans &7| &cThat clan does not exist!"));
            return;
        }

        for (String clanMember : ChatColor.stripColor(clanConfiguration.getClanMembers(clan)).replace(" ", "").split(",")) {
            if (!clanMember.isEmpty()) {
                OfflinePlayer clanMemberPlayer = Bukkit.getOfflinePlayer(UUID.fromString(clanMember));

                if (clanMemberPlayer.hasPlayedBefore() && clanMemberPlayer.getPlayer() != null) {
                    Double clanMemberKills = getData(clanMemberPlayer.getPlayer(), "kills", "playerdata.yml");
                    Double clanMemberDeaths = getData(clanMemberPlayer.getPlayer(), "deaths", "playerdata.yml");

                    clanMoney = clanMoney + economy.getBalance(clanMemberPlayer);
                    clanKills = clanKills + clanMemberKills;
                    clanDeaths = clanDeaths + clanMemberDeaths;
                }

            }
        }

        double clanPoints = (clanMoney / 100000) + (clanKills * 4) - clanDeaths; //The math to ger the points
        clanPoints = Math.round(clanPoints); //rounding it to the nearest integer

        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&m&3--------------------------------"));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lCLAN STATS &f(" + clan + ")"));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3Kills : &f" + clanKills));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3Deaths : &f" + clanDeaths));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3Money : &f" + clanMoney));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3Points : &f" + clanPoints));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7(The way points are calculated are)"));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7(clanMoney / 100000) + (clanKills * 4) - clanDeaths)"));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&m&m&3--------------------------------"));
    }

    public Double getPoints(String clan){

        ClanConfiguration clanConfiguration = new ClanConfiguration();

        Double clanKills = (double) 0;
        Double clanDeaths = (double) 0;
        Double clanMoney = (double) 0;

        Economy economy = MsClansAddons.getEconomy();

        if(clanConfiguration.getClan(clan) == null){
            return (double) 0;
        }

        for (String clanMember : ChatColor.stripColor(clanConfiguration.getClanMembers(clan)).replace(" ", "").split(",")) {
            if (clanMember != null) {
                OfflinePlayer clanMemberPlayer = Bukkit.getOfflinePlayer(clanMember);

                if( clanMemberPlayer.getPlayer() != null){
                    Double clanMemberKills = getData(clanMemberPlayer.getPlayer(),"kills", "playerdata.yml");
                    Double clanMemberDeaths = getData(clanMemberPlayer.getPlayer(),"deaths", "playerdata.yml");

                    clanMoney = clanMoney + economy.getBalance(clanMemberPlayer);
                    clanKills = clanKills + clanMemberKills;
                    clanDeaths = clanDeaths + clanMemberDeaths;
                }
            }
        }

        double clanPoints = (clanMoney / 100000) + (clanKills * 4) - clanDeaths; //The math to ger the points
       return (double) Math.round(clanPoints);
    }
    public Map<String, Double> getClanTop(){

        List<String> clansList = listClans("plugins/Clans");

        Map<String, Double> clansPointsMap = new HashMap<>();

        for (String fileName : clansList) {
            Double points = getPoints(fileName); // Implement the getPoints method.
            clansPointsMap.put(fileName, points);
        }

        clansPointsMap = bubbleSortClans(clansPointsMap);
        clansPointsMap = chopHashMap(clansPointsMap, 10);

        return clansPointsMap;
    }

    // Yea this toke way to long and is so extra but who cares \-_-/
    public static <K, V> HashMap<K, V> chopHashMap(Map<? extends K, ? extends V> originalMap, int desiredSize) {
        HashMap<K, V> choppedHashMap = new HashMap<>();

        for (Map.Entry<? extends K, ? extends V> entry : originalMap.entrySet()) {
            if (choppedHashMap.size() < desiredSize) {
                choppedHashMap.put(entry.getKey(), entry.getValue());
            } else {
                break; // Stop adding elements once we reach the desired size
            }
        }

        return choppedHashMap;
    }

    public static List<String> listClans(String directoryPath) {
        List<String> yamlFileNames = new ArrayList<>();
        File directory = new File(directoryPath);

        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles((dir, name) -> name.endsWith(".yml"));

            if (files != null) {
                for (File file : files) {
                    // Remove the .yml extension
                    String fileName = file.getName().replace(".yml", "");
                    yamlFileNames.add(fileName);
                }
            }
        }

        return yamlFileNames;
    }

    public static Map<String, Double> bubbleSortClans(Map<String, Double> clansPointsMap) {
        Map<String, Double> sortedMap = new HashMap<>(clansPointsMap);

        boolean swapped;
        do {
            swapped = false;
            for (Entry<String, Double> entry : sortedMap.entrySet()) {
                String currentClan = entry.getKey();
                Double currentPoints = entry.getValue();
                Entry<String, Double> nextEntry = getNextEntry(sortedMap, currentClan);

                if (nextEntry != null) {
                    String nextClan = nextEntry.getKey();
                    Double nextPoints = nextEntry.getValue();
                    if (currentPoints > nextPoints) {
                        // Swap the positions of the clans in the sortedMap
                        sortedMap.put(currentClan, nextPoints);
                        sortedMap.put(nextClan, currentPoints);
                        swapped = true;
                    }
                }
            }
        } while (swapped);

        return sortedMap;
    }

    public static Entry<String, Double> getNextEntry(Map<String, Double> map, String key) {
        boolean found = false;
        for (Entry<String, Double> entry : map.entrySet()) {
            if (found) {
                return entry;
            }
            if (entry.getKey().equals(key)) {
                found = true;
            }
        }
        return null;
    }
}

