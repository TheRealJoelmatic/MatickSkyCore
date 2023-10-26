package org.matic.msclansaddons.data;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class saveManger {

    // Save home data for a player
    public static void saveData(String identifier, Double data, String dataName, String fileName) {

        createPlayerDataFile();

        //get the file
        File playerDataFile = new File("plugins/MsClansAddons/" + fileName);
        YamlConfiguration playerData = YamlConfiguration.loadConfiguration(playerDataFile);

        //save the values
        playerData.set( identifier + "." + dataName, data);

        // Save changes to the file
        try {
            playerData.save(playerDataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("MS | Saved data for :" +  identifier + " data value: " + data + " Data name: " + dataName);
    }
    public static void saveData(String identifier, String data, String dataName, String fileName) {

        createPlayerDataFile();

        //get the file
        File playerDataFile = new File("plugins/MsClansAddons/" + fileName);
        YamlConfiguration playerData = YamlConfiguration.loadConfiguration(playerDataFile);

        //save the values
        playerData.set( identifier + "." + dataName, data);

        // Save changes to the file
        try {
            playerData.save(playerDataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("MS | Saved data for :" +  identifier + " data value: " + data + " Data name: " + dataName);
    }

    public static double getData(String identifier, String dataName, String fileName) {

        createPlayerDataFile();

        //get the file
        File playerDataFile = new File("plugins/MsClansAddons/" + fileName);
        YamlConfiguration playerData = YamlConfiguration.loadConfiguration(playerDataFile);

        //get the cords for the house
        if (playerData.contains(identifier)) {
            return playerData.getDouble(identifier + "." + dataName);
        }
        else
        {
            System.out.println("FILE OR UUID NOT FOUND! : " + identifier + " dataName: " + dataName + " Filename: " + fileName);
        }
        // Return null if player dont have anything
        return 0;
    }

    public static String getStringData(String identifier, String dataName, String fileName) {

        createPlayerDataFile();

        //get the file
        File playerDataFile = new File("plugins/MsClansAddons/" + fileName);
        YamlConfiguration playerData = YamlConfiguration.loadConfiguration(playerDataFile);

        //get the cords for the house
        if (playerData.contains(identifier)) {
            return playerData.getString(identifier + "." + dataName);
        }
        else
        {
            System.out.println("FILE OR UUID NOT FOUND! : " + identifier + " dataName: " + dataName + " Filename: " + fileName);
        }
        // Return null if player dont have anything
        return null;
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

        playerDataFile = new File("plugins/MsClansAddons/uuid.yml");
        if (playerDataFile.exists()) return;
        try {
            playerDataFile.createNewFile(); // Create the file if it doesn't exist
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
