package org.matic.msclansaddons.events;

import Clans.Events.ClanCreateEvent;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class clanCreate implements Listener {
    @EventHandler
    public void privateChat(ClanCreateEvent e) {
        Player owner = e.getOwner();
        String clan = e.getClanName();

        String createMessage = "%aqua_player_rank% &7| " + owner.getName() + " &3Created the clan: &b" + clan;
        createMessage =  PlaceholderAPI.setPlaceholders(owner, createMessage);

        Bukkit.broadcastMessage( ChatColor.translateAlternateColorCodes('&', createMessage));
    }
}