package org.matic.msclansaddons.data;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
public class saveManger {

    // Save home data for a player
    public static void saveData(Player player, Double data, String dataName, String fileName) {

        createPlayerDataFile();

        //get the file
        File playerDataFile = new File("plugins/MsClansAddons/" + fileName);
        YamlConfiguration playerData = YamlConfiguration.loadConfiguration(playerDataFile);

        //save the values
        playerData.set(player.getUniqueId() + "." + dataName, data);

        // Save changes to the file
        try {
            playerData.save(playerDataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("MS | Saved data for" + player.getName() + " data value: " + data + " Data name: " + dataName);
    }

    public static double getData(Player player, String dataName, String fileName) {

        createPlayerDataFile();

        //get the file
        File playerDataFile = new File("plugins/MsClansAddons/" + fileName);
        YamlConfiguration playerData = YamlConfiguration.loadConfiguration(playerDataFile);

        //get the cords for the house
        if (playerData.contains(player.getUniqueId().toString()) && player.getUniqueId() != null) {
            double returnData = playerData.getDouble(player.getUniqueId() + "." + dataName);
            return returnData;
        }
        else
        {
            System.out.println("FILE OR UUID NOT FOUND!");
        }
        // Return null if player dont have anything
        return 0;
    }

    public static double getDataOfflinePlayer(OfflinePlayer player, String dataName, String fileName) {

        createPlayerDataFile();

        //get the file
        File playerDataFile = new File("plugins/MsClansAddons/" + fileName);
        YamlConfiguration playerData = YamlConfiguration.loadConfiguration(playerDataFile);

        //
        if (playerData.contains(player.getUniqueId().toString()) && player.getUniqueId() != null) {
            double returnData = playerData.getDouble(player.getUniqueId() + "." + dataName);
            return returnData;
        }
        else
        {
            System.out.println("FILE OR UUID NOT FOUND!");
        }
        // Return null if player dont have anything
        return 0;
    }
    // Create the folder and file if they don't exist
    public static void createPlayerDataFile() {
        File playerDataFolder = new File("plugins/MsClansAddons");
        if (!playerDataFolder.exists()) {
            playerDataFolder.mkdirs(); // Create the folder if it doesn't exist
        }

        File playerDataFile = new File("plugins/MsClansAddons/playerdata.yml");
        if (playerDataFile.exists()) return;
        try {
            playerDataFile.createNewFile(); // Create the file if it doesn't exist
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
