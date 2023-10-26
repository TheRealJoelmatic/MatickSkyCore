package org.matic.msclansaddons.commands;

import Clans.ClanConfiguration;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.matic.msclansaddons.MsClansAddons;

import java.io.File;
import java.util.*;
import java.util.Map.Entry;

import static org.matic.msclansaddons.data.saveManger.getData;
import static org.matic.msclansaddons.data.saveManger.getStringData;


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
            getPointsMethod(event.getPlayer(), args[2]);
        } else if (args.length > 1 && args[0].equalsIgnoreCase("clan") && args[1].equalsIgnoreCase("stats")) {
            event.setCancelled(true);
            ClanConfiguration clanConfiguration = new ClanConfiguration();
            getPointsMethod(event.getPlayer(), clanConfiguration.getClan(event.getPlayer()));
        }

        if (args.length > 1 && args[0].equalsIgnoreCase("clan") && args[1].equalsIgnoreCase("top")) {
            event.setCancelled(true);
            Map<String, Double> clansTop = getClanTop();

            Player player = event.getPlayer();

            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&m&3--------------------------------"));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lCLAN TOP"));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', " "));

            int loopNumber = 1;
            for (Map.Entry<String, Double> entry : clansTop.entrySet()) {
                if (loopNumber > 10) {
                    break; // Break the loop after displaying the first 10 elements.
                }

                String name = entry.getKey();
                Double value = entry.getValue();

                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6 " + loopNumber + " &7- &f" + name + " &7- &3" + value + " &7(Points)"));

                loopNumber++;
            }


            player.sendMessage(ChatColor.translateAlternateColorCodes('&', " "));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&m&m&3--------------------------------"));

        }
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

                String clanMemberUUID = getStringData(clanMember, "uuid", "uuid.yml");
                Double clanMemberKills = getData(clanMemberUUID,"kills", "playerdata.yml");
                Double clanMemberDeaths = getData(clanMemberUUID,"deaths", "playerdata.yml");

                //get the money :)

                File playerDataFile = new File("plugins/BetterEconomy/balances.yml");
                YamlConfiguration playerData = YamlConfiguration.loadConfiguration(playerDataFile);
                if (playerData.contains(clanMemberUUID)) {
                    clanMoney = clanMoney + playerData.getDouble(clanMemberUUID);
                }
                else{
                    System.out.println(clanMemberUUID + " Failed to get there p's");
                }

                clanKills = clanKills + clanMemberKills;
                clanDeaths = clanDeaths + clanMemberDeaths;
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
            if (!clanMember.isEmpty()) {

                String clanMemberUUID = getStringData(clanMember, "uuid", "uuid.yml");
                Double clanMemberKills = getData(clanMemberUUID,"kills", "playerdata.yml");
                Double clanMemberDeaths = getData(clanMemberUUID,"deaths", "playerdata.yml");

                //get the money :)

                File playerDataFile = new File("plugins/BetterEconomy/balances.yml");
                YamlConfiguration playerData = YamlConfiguration.loadConfiguration(playerDataFile);
                if (playerData.contains(clanMemberUUID)) {
                    clanMoney = clanMoney + playerData.getDouble(clanMemberUUID);
                }
                else{
                    System.out.println(clanMemberUUID + " Failed to get there p's");
                }

                clanKills = clanKills + clanMemberKills;
                clanDeaths = clanDeaths + clanMemberDeaths;
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
            System.out.println(fileName + " | " + points);
        }

        clansPointsMap = quickSortClans(clansPointsMap);

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

    public Map<String, Double> quickSortClans(Map<String, Double> clansPointsMap) {
        List<Map.Entry<String, Double>> entryList = new ArrayList<>(clansPointsMap.entrySet());
        quickSort(entryList, 0, entryList.size() - 1);

        // Create a new map from the sorted entries
        Map<String, Double> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<String, Double> entry : entryList) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

    private void quickSort(List<Map.Entry<String, Double>> entryList, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(entryList, low, high);
            quickSort(entryList, low, pivotIndex - 1);
            quickSort(entryList, pivotIndex + 1, high);
        }
    }

    private int partition(List<Map.Entry<String, Double>> entryList, int low, int high) {
        double pivot = entryList.get(high).getValue();
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (entryList.get(j).getValue() > pivot) {
                i++;
                swap(entryList, i, j);
            }
        }

        swap(entryList, i + 1, high);
        return i + 1;
    }

    private void swap(List<Map.Entry<String, Double>> entryList, int i, int j) {
        Map.Entry<String, Double> temp = entryList.get(i);
        entryList.set(i, entryList.get(j));
        entryList.set(j, temp);
    }

}

