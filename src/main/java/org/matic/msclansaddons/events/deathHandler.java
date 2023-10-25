package org.matic.msclansaddons.events;

import Clans.ClanConfiguration;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import static org.matic.msclansaddons.data.saveManger.getData;
import static org.matic.msclansaddons.data.saveManger.saveData;


public class deathHandler implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player killed = event.getEntity(); // Get the player who was killed
        Player killer = event.getEntity().getKiller(); // Get the player who killed

        if (killed == null ) return;

        Double playersKills = (double) 0;

        //save there stats

        playersKills = getData(killed,"deaths", "playerdata.yml") + 1;
        saveData(killed, playersKills,"deaths", "playerdata.yml");

        if(killer == null) return;

        playersKills = getData(killer,"kills", "playerdata.yml") + 1;
        saveData(killer, playersKills,"kills", "playerdata.yml");

        //get there names
        String killedName = killed.getName();
        String killerName = killer.getName();

        //get there clan
        ClanConfiguration clanConfiguration = new ClanConfiguration();
        String killedClan = clanConfiguration.getClan(killed);
        String killerClan = clanConfiguration.getClan(killer);


        if(killedClan == null){
            killedClan = "No clan";
        } else if (killerClan == null) {
            killerClan = "No clan";
        }
        event.setDeathMessage(null);

        String deathMessageP1 = "&7[" + killerClan + "]" + " %aqua_player_rank% &7| " + killerName;
        String deathMessageP2 = "&r &cKilled &7[" + killedClan + "]" + " %aqua_player_rank%  &7| " + killedName;
        deathMessageP1 = PlaceholderAPI.setPlaceholders(killer, deathMessageP1);
        deathMessageP2 = PlaceholderAPI.setPlaceholders(killed, deathMessageP2);
        String broadcastMessage = ChatColor.translateAlternateColorCodes('&', deathMessageP1 + deathMessageP2);
        Bukkit.broadcastMessage(broadcastMessage);


        // Do something with the player names
        System.out.println(killedName + " was killed by " + killerName);

    }
}
